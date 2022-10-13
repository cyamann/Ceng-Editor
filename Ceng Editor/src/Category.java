
public class Category {
private Object category;
private Category down;
private Category up;
private Item right;
public Category(Object datatoAdd) {
	category = datatoAdd;
	down = null;
	up = null;
	right = null;
}
public Category getUp() {
	return up;
}
public void setUp(Category up) {
	this.up = up;
}
public Object getCategory() {
	return category;
}
public void setCategory(Object category) {
	this.category = category;
}
public Category getDown() {
	return down;
}
public void setDown(Category down) {
	this.down = down;
}
public Item getRight() {
	return right;
}
public void setRight(Item right) {
	this.right = right;
}

}
