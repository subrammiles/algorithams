

1.Stack follows LIFO and stack follows FIFO


 11,33,44,5


Push eleemmts to on stack


stack=11,33,44,5
 5 is top of the stack .Only we can pop this elements as it is stack .But we need to pop 11 as it is first element
 
 # Solution:   
 1.Take 2 stacks
 2.
 stack 1:11,33,44,5
 stack 2:
 3.Pop all elements from stack1 to stack 2
    stack 1:
    stack 2:5,44,33,11
 4.Now pop 11 from top of stack2
    stack 1:
    stack 2:5,44,33
 5.Our peek  work is done.Now move bacl the eleemts from stack2 to stack 1
        stack 1:33,44,5
        stack 2:
 
You can implement abovr login by
Making a dequeue  costly
Making an enqueue costly.
