public class MultiLinkedList {
Category head;
Category tail;
int upnumber = 1;
int downnumber = 20;
int number= 0;
int foundcount = 0;
boolean foundflag = false;
int[] category = new int[1000];
int[] item = new int[1000];
public void addCategory(Object dataToAdd) {
	
	Category newnode = new Category(dataToAdd);
	if(head == null) {
		head = newnode;
		tail = newnode;
	}
	else {
		newnode.setUp(tail);
		tail.setDown(newnode);
		tail = newnode;
	}
}
public void addItem(Object category,Object item) {
	if(head == null)
		System.out.println("Add a category before item.");
	else {
		Category temp = tail;
		while(temp != null) {
			if(category.equals(temp.getCategory())) {
				Item temp2 = temp.getRight();
				if(temp2 == null) {
					Item newnode = new Item(item);
					temp.setRight(newnode);
				}
				else {
					while(temp2.getNext() != null)
						temp2 = temp2.getNext();
					Item newnode = new Item(item);
					temp2.setNext(newnode);
				}
			}
			temp = temp.getUp();
		}
	}
}
public int sizeCategory() {
	int count = 0;
	if(tail == null)
		System.out.println("Linked list is empty.");
	else {
		Category temp = tail;
		while(temp != null) {
			count++;
			temp = temp.getUp();
		}
	}
	return count;
}
public int sizeItem() {
	int count = 0;
	if(tail == null)
		System.out.println("Linked List is empty.");
	else {
		Item item = head.getRight();
		while(item.getNext() != null) {
			count++;
			item = item.getNext();
		}
	}
	return count;
}
public boolean searchboolean(Object obj) {
	if(head == null)
		System.out.println("The linked list is empty");
	else {
		Category temp = head;
		while(temp != null) {
			if(obj == (Object) temp.getCategory()) {
				return true;
			}
			temp = temp.getDown();
		}
	}
	return false;
}
public Item search(Object obj) {
	if(head == null)
		System.out.println("The linked list is empty");
	else {
		Category temp = head;
		while(temp != null) {
			if(obj == (Object) temp.getCategory()) {
				return temp.getRight();
			}
			temp = temp.getDown();
		}
	}
	return null;
}
@SuppressWarnings("unused")
public void searchitem(MultiLinkedList page,Object letter) {
	Category temp = page.head;
	Item temp2 = temp.getRight();
	boolean flag = false;
	foundcount = 0;
	for(int i = upnumber; i < downnumber + 1; i++) {
		for(int j = 1; j < 60; j++) {
			if(j == 1 && i!=upnumber) {
				if(temp != null) {
					temp = temp.getDown();
					if(temp != null)
						temp2 = temp.getRight();
				}
				else {
					break;
				}
			}
			if(letter.toString().length() > 1) {
				int counter = 0;
				while(counter != letter.toString().length()) {
					if(temp2 != null && (char)temp2.getData() == (letter.toString().charAt(counter))) {
						flag = true;
						category[foundcount] = (int) temp.getCategory();
						item[foundcount] = j;
						if(temp2 == null) {
							if(temp == null)
								break;
							else {
								i++;
								j = 1;
								counter++;
								temp = temp.getDown();
								temp2 = temp.getRight();
							}
						}
						else {
							j++;
							counter++;
							temp2 = temp2.getNext();
						}
					}
					else {
						flag = false;
						if(temp2 == null) {
							if(temp == null)
								break;
							else {
								temp = temp.getDown();
								temp2 = temp.getRight();
							}
						}
						else {
							temp2 = temp2.getNext();
						}
						counter=0;
						break;
					}
				}
				if(flag) {
					j--;
					foundcount++;
				}
			}
			else {
				while(true) {
					if(temp2 != null && temp2.getData().toString().equals(letter.toString())) {
						category[foundcount] = i;
						item[foundcount] = j;
						foundcount++;
						if(temp2 == null) {
							if(temp == null)
								break;
							else {
								temp = temp.getDown();
								temp2 = temp.getRight();
								break;
							}
						}
						else {
							temp2 = temp2.getNext();
							break;
						}
					}
					else {
						if(temp2 == null) {
							if(temp == null)
								break;
							else {
								temp = temp.getDown();
								temp2 = temp.getRight();
								break;
							}
						}
						else {
							temp2 = temp2.getNext();
							break;
						}
					}
				}
			}
		}
	}
}


public int[] getcategorynumber() {
	return category;
}
public int[] getItemnumber() {
	return item;
}
public int getFoundCount() {
	return foundcount;
}
public void displayheadtotail() {
	if(head == null)
		System.out.println("Linked list is empty.");
	else {
		Category temp = head;
		while(temp != null) {
			Item temp2 = temp.getRight();
			while(temp2 != null) {
				System.out.print(String.valueOf(temp2.getData()));
				temp2 = temp2.getNext();
			}
			System.out.println();
			temp = temp.getDown();
		}
	}
}
public MultiLinkedList pageup(MultiLinkedList page) {
	upnumber--;
	page.addCategory(upnumber);
	int counter = 1;
	while(counter != 61) {
		page.addItem(upnumber, ' ');
		counter++;
	}
	return page;
}
public MultiLinkedList pagedown(MultiLinkedList page) {
	downnumber++;
	page.addCategory(downnumber);
	int counter = 1;
	while(counter != 61) {
		page.addItem(downnumber, ' ');
		counter++;
	}
	return page;
}

public int getUpnumber() {
	return upnumber;
}
public void setUpnumber(int upnumber) {
	this.upnumber = upnumber;
}
public int getDownnumber() {
	return downnumber;
}
public void setDownnumber(int downnumber) {
	this.downnumber = downnumber;
}
public void deleteItem(Object category, Object item) {
	while(true) {
		if(tail == null)
			{
			System.out.println("Linked list is empty");
			break;
			}
		else if(tail.getCategory().equals(category)) {
			Item temp = tail.getRight();
			while(true) {
				if(temp.getData() == null) {
					System.out.println("There is no item.");
					break;
				}
				else if(temp != null && temp.getData().equals(item)) {
					temp.setData(temp.getNext().getData());
					Item temp2 = temp.getNext(); 
					temp.setNext(temp2.getNext());
					break;
				}
				else {
					
					temp = temp.getNext();
				}
			}
			break;
		}
		else {
			tail = tail.getUp();
		}
	}
}
public void deleteCategory(Object c) {
if(head == null)
	System.out.println("Linked list is empty.");
else if(head.getCategory().equals(c) ){
	head = head.getDown();
}
else {
	Category temp = head;
	while(temp.getDown() != null && temp.getCategory().equals(c)) {
		temp = temp.getDown();
	}
	if(temp == null)
		System.out.println("Linked list is empty.");
	else {
		temp.setDown(temp.getDown());
	}
}
}
public String getSelected(MultiLinkedList selection) {
	String selected = "";
	Item item = head.getRight();
	number = item.sizeitem(item) + 1;
	for(int i = 0; i < number; i++) {
		selected += item.getData();
		item = item.getNext();
	}
	return selected;
}
}
