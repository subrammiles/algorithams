
class Node:
    def __init__(self,data):
        self.data=data;
        self.next=None;
class SS:
    def __init__(self):
        self.head=None;
        self.tail=None;
    
    def addNode(self,data):
        newNode=Node(data);

        if(self.head==None):
            self.head=newNode;
            self.tail=newNode;
        else :
            self.tail.next=newNode;
            self.tail=newNode;
    def dispaly(self):
        current=self.head;
        if(self.head==None):
            print("Null")
            
            return;
        while(current!=None):
            print(current.data)
            current=current.next;
sList=SS();
sList.addNode(1);
sList.addNode(2);
sList.dispaly();

