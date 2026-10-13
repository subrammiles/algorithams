#!/usr/bin/env bash
# Compiles everything and runs every self-checking example. Exit code 0 = all passed.
#   scripts/check_all.sh              all languages
#   scripts/check_all.sh java go      only some
# Java: the whole tree is compiled; only java/src/interview mains are run (they assert their
# answers). Older Java programs elsewhere may wait for keyboard input, so they are compile-checked.
set -uo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
LANGS=("$@")
[ $# -eq 0 ] && LANGS=(java python javascript go)
failed=0
report() { if [ "$1" -eq 0 ]; then echo "  ok   $2"; else echo "  FAIL $2"; failed=1; fi; }

for lang in "${LANGS[@]}"; do
  echo "== $lang"
  case $lang in
    java)
      out=$(mktemp -d)
      javac -nowarn -d "$out" $(find "$ROOT/java/src" -name '*.java') 2>&1 | grep -v '^Note:'
      report "${PIPESTATUS[0]}" "javac (whole java/src tree)"
      for f in $(grep -rlE 'static +void +main' "$ROOT/java/src/interview" | sort); do
        cls=$(realpath --relative-to="$ROOT/java/src" "$f" | sed 's/\.java$//; s|/|.|g')
        timeout 60 java -cp "$out" "$cls" </dev/null >/dev/null 2>&1; report $? "$cls"
      done ;;
    python)
      for f in $(find "$ROOT/python" -name '*.py' | sort); do
        timeout 60 python3 "$f" </dev/null >/dev/null 2>&1; report $? "${f#$ROOT/}"
      done ;;
    javascript)
      for f in $(find "$ROOT/javascript" -name '*.js' | sort); do
        timeout 60 node "$f" </dev/null >/dev/null 2>&1; report $? "${f#$ROOT/}"
      done ;;
    go)
      (cd "$ROOT/go" && go vet ./... && go test ./...) 2>&1 | grep -v 'no test files' | sed 's/^/  /'
      report "${PIPESTATUS[0]}" "go vet + go test" ;;
  esac
done
[ $failed -eq 0 ] && echo "ALL PASSED" || echo "SOME CHECKS FAILED"
exit $failed
