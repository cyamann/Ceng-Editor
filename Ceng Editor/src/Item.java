public class Item {
private Item next;
Object data;
public Item(Object datatoAdd) {
	this.data = datatoAdd;
	next = null;
}
public Item() {
	
}
public Item getNext() {
	return next;
}
public void setNext(Item next) {
	this.next = next;
}
public Object getData() {
	return data;
}
public void setData(Object data) {
	this.data = data;
}
public boolean isEmpty() {
	return (getNext() == null);
}
public int sizeitem(Item item) {
	int count = 0;
	Item temp  = item;
	while(!temp.isEmpty()) {
		temp = temp.getNext();
		count++;
	}
	return count;
}
}
