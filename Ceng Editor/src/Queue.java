public class Queue {
private int front,rear;
private Object[] elements;
public Queue(int capacity) {
	elements = new Object[capacity];
	front = 0;
	rear = -1;
}
public void Enqueue(Object element) {
	if(isFull()) {
		System.out.println("System overflow.");
	}
	else {
		
		rear++;
		elements[rear] = element;
	}
}
public Object Dequeue() {
	if(isEmpty()) {
		return null;
	}
	else {
		Object data = elements[front];
		elements[front] = null;
		front++;
		return data;
	}
}
public Object peek() {
	if(isEmpty()) {
		return null;
	}
	else {
		return elements[front];
	}
}
public boolean isEmpty() {
	return (rear<front);
}
public boolean isFull() {
	return (rear + 1 == elements.length);
}
public int size() {
	return (rear+1-front);
}
}
