import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import enigma.console.TextAttributes;
import java.awt.Color;

public class Game {
	public enigma.console.Console cn = Enigma.getConsole("Keyboard Mouse 2",100,30,28,2); // col,row,fontsize,fonttype
	   public enigma.console.TextWindow cnt = cn.getTextWindow();
	   public static TextAttributes att0 = new TextAttributes(Color.white, Color.black);  //foreground, background color
	   public static TextAttributes att1 = new TextAttributes(Color.black, Color.white);
	   public TextMouseListener tmlis; 
	   public KeyListener klis;  

   // ------ Standard variables for mouse and keyboard ------
	   public int mousepr;          // mouse pressed?
	   public int mousex, mousey;   // mouse text coords.
	   public int keypr;   // key pressed?
	   public int rkey;    // key   (for press/release)
	   public int rkeymod;      // key modifiers
	   public int capslock=0;   // 0:off    1:on
   // ----------------------------------------------------
	   boolean select = false,upflag = false,downflag = false;
	   int direction = 0,row = 1,yon = 0,next = 1;
	   int up = 0, down = 0;
	   String mode = "Overwrite",selected = "",alignment="Align left";Object letter = "";
	   boolean mod = false;
	   File inFile = new File("screen.txt");  
	   int lineCounter = 1;
	   MultiLinkedList multi = new MultiLinkedList();
	   MultiLinkedList selection = new MultiLinkedList();
	   int line = 1;
	   MultiLinkedList lines = new MultiLinkedList();
	   MultiLinkedList scroll = new MultiLinkedList();
	   MultiLinkedList page = new MultiLinkedList();
	   int[] categorynumber = new int[1000];
	   int[] itemnumber = new int[1000];
	   Object replace = "";
	   Scanner sc = new Scanner(System.in);
	   public void F7(Object[][] screen, int px, int py) throws InterruptedException {
 		  int counter = 0;int b = px, d = py;
 		  
 		  for(int i = 1; i < 21; i++) {
 			  for(int j = 1; j < 60; j++) {
 				  if(i == categorynumber[next-1] && j == itemnumber[next-1]) {
 					  Queue replaced = new Queue(2000);
 					  px = itemnumber[next-1] + 1;
 					  py = categorynumber[next-1];
 					  while(true) {
 						  replaced.Enqueue(screen[py][px]);
 						  if(px == 60 && py == 21)
 							  break;
 						  if(px != 60)
 							  px++;
 						  else {
 							  if(py != 21) {
 								  py++;
 								  px =1;
 							  }
 						  }
 					  }
 					  if(replace.toString().length()>  letter.toString().length()) {
 						  while(counter != replace.toString().length()) {
 							  int a = itemnumber[next-1]+ counter - letter.toString().length() + 1;
     						  cnt.setCursorPosition(a,categorynumber[next-1]);
     						  screen[categorynumber[next-1]][a] = replace.toString().charAt(counter);
     						  System.out.print(screen[categorynumber[next-1]][a]);
     						  counter++;
     					  } 
 						  j += replace.toString().length()-1;
 					  }
 					  else if(replace.toString().length() <  letter.toString().length()) {
 						 while(counter != replace.toString().length()) {
							  int a = itemnumber[next-1]+ counter - letter.toString().length() + 1;
    						  cnt.setCursorPosition(a,categorynumber[next-1]);
    						  screen[categorynumber[next-1]][a] = replace.toString().charAt(counter);
    						  System.out.print(screen[categorynumber[next-1]][a]);
    						  counter++;
    					  } 
						  j --;
 					  }
 					  else {
 						  while(counter != replace.toString().length()) {
 							  cnt.setCursorPosition(itemnumber[next-1]-replace.toString().length() + counter + 1,categorynumber[next-1]);
     						  screen[categorynumber[next-1]][itemnumber[next-1]-replace.toString().length() + counter+ 1] = replace.toString().charAt(counter);
     						  System.out.print(screen[categorynumber[next-1]][itemnumber[next-1]-replace.toString().length() + counter+ 1]);
     						  counter++;
 						  }
 					  }
 					  for(int k = 1; k < 21; k++) {
 						  for(int l = 1; l < 60; l++) {
 							  if(replaced.isEmpty() || replaced.peek() == (Object)'-')
 								  break;
 							  if(k ==1 && l ==1) {
 								  if(k != i) {
     								  while(k!=i) {
     									  k++;
     								  }
     							  }
     							  if(k == i) {
     								  while(l != j+1) {
     									  l++;
     								  }
     								  i++;
     							  }
 							  }
 							  screen[k][l] = replaced.peek();
 							  cnt.setCursorPosition(l,k);
 							  System.out.print(replaced.Dequeue());
 						  }
 					  }
 				  }
 			  }
 		  }
 		  screen(screen);
 		  cnt.setCursorPosition(65,19);
 		  System.out.print("                         ");
 		  cnt.setCursorPosition(65,20);
 		  System.out.print("                         ");
 		  cnt.setCursorPosition(65,21);
 		  System.out.print("                         ");
 		  cnt.setCursorPosition(65,19);
 		  System.out.print("Done!");
 		  Thread.sleep(3000);
 		  cnt.setCursorPosition(65,19);
 		  System.out.print("           ");
 		  px = b;
 		  py= d;
 		  cnt.setCursorPosition(px,py);
	   }
	public void beautifuldisplay(MultiLinkedList page,Object[][] screen,int px,int py) {
		Category temp = page.head;
		Item temp2 = temp.getRight();
		int gx = 1, gy = 1,row2 = row;
		while((row2 - row != 20)) {
			if(temp!= null &&(int) temp.getCategory() == row2) {
				temp2 = temp.getRight();
				cnt.setCursorPosition(gx,gy);
				while(temp2 != null) {
					screen[gy][gx] = temp2.getData();
					System.out.print(temp2.getData());
					temp2 = temp2.getNext();
					gx++;
					if(gx == 60)
						break;
				}
				gy++;
				gx = 1;
				row2++;
				temp = page.head;
				if(gy == 21)
					break;
				if(temp == null)
					break;
				else
					continue;
			}
			else {
				if(temp != null)
					temp = temp.getDown();
				else
					break;
			}
		}
	}
   public Object[][] displayscreen() throws FileNotFoundException {
	   inFile = new File("screen.txt");                                 
	   Scanner sc = new Scanner(inFile); 
	   Object[][] screen = new Object[22][62];
	   char[] chr = new char[62];
	   String line ;
	   for(int i = 0;i<22;i++){
		   line = sc.nextLine();
		   chr = line.toCharArray();
		   for(int j =0;j<62;j++) {
			   screen[i][j] = chr[j];
		   }
	   }	   
	   for(int i = 0;i<22;i++) {		   
	    	for(int j =0;j<62;j++) {	    		
	    		System.out.print(screen[i][j]);
	    	}
	    	System.out.println();
	    }
	   cn.getTextWindow().setCursorPosition(65,0);
	   System.out.println("F1 : Selection start");
	   cn.getTextWindow().setCursorPosition(65,1);
	   System.out.println("F2 : Selection end");
	   cn.getTextWindow().setCursorPosition(65,2);
	   System.out.println("F3 : Cut");
	   cn.getTextWindow().setCursorPosition(65,3);
	   System.out.println("F4 : Copy");
	   cn.getTextWindow().setCursorPosition(65,4);
	   System.out.println("F5 : Paste");
	   cn.getTextWindow().setCursorPosition(65,5);
	   System.out.println("F6 : Find");
	   cn.getTextWindow().setCursorPosition(65,6);
	   System.out.println("F7 : Replace");
	   cn.getTextWindow().setCursorPosition(65,7);
	   System.out.println("F8 : Next");
	   cn.getTextWindow().setCursorPosition(65,8);
	   System.out.println("F9 : Align left");
	   cn.getTextWindow().setCursorPosition(65,9);
	   System.out.println("ALT : Align Right");
	   cn.getTextWindow().setCursorPosition(65,10);
	   System.out.println("F10 : Justify");
	   cn.getTextWindow().setCursorPosition(65,11);
	   System.out.println("F11 : Load");
	   cn.getTextWindow().setCursorPosition(65,12);
	   System.out.println("F12 : Save");
	   cn.getTextWindow().setCursorPosition(65,15);
	   System.out.println("Mode : " + mode);
	   cn.getTextWindow().setCursorPosition(65,14);
	   System.out.println("Alignment : " + alignment);
	   return screen;
   }
   public Object[][] backspace(Object[][] screen,int px,int py) {
	   Queue backspace = new Queue(2000);
	   if(px!=1) {
		   px--;
		   screen[py][px] = (Object) ' ';
     	  for(int i = 1; i < 21; i++) {
     		  for(int j = 1; j < 60; j++) {
     			  if(i == 1 && j == 1) {
     				  while(i != py) {
     					  i++;
     				  }
     				  while(j != px + 1) {
     					  j++;
     				  }
     			  }
     			  backspace.Enqueue(screen[i][j]);
     		  }
     	  }
     	  for(int i = 1;i < 21; i++) {
     		  for(int j = 1; j < 60; j++) {
     			  if(backspace.isEmpty())
     				  break;
     			 if(i == 1 && j == 1) {
    				  while(i != py) {
    					  i++;
    				  }
    				  while(j != px) {
    					  j++;
    				  }
    			  }
     			 screen[i][j] = backspace.peek();
     			 cn.getTextWindow().output(j,i,(char) backspace.Dequeue());
     		  }
     	  }
     	  cnt.setCursorPosition(px, py);
	   }
	   return screen;
   }
   public Object[][] cut(Object[][] screen, int px, int py){
	   if(px!=1) {
	     	  screen[py][px] = (char)' ';
	     	  cn.getTextWindow().output(px,py,' ');
	     	  cnt.setCursorPosition(px, py);
	     	  for(int i = py; i < 21; i++) {
	     		  for(int j = px; j < 60; j++) {
	     			  screen[i][j] = screen[i][j+1];
	     			 cn.getTextWindow().output(j-1,i,(char) screen[i][j-1]);
	     			  if(j == 60 && i != 20) {
	     				  screen[i][j] = screen[i+1][1];
	     				 cn.getTextWindow().output(j,i,(char) screen[i][j]);
	     			  }
	     		  }
	     	  }
		   }
		   return screen;
   }
   public void screen(Object[][] screen) {
	   cnt.setCursorPosition(0,0);
	   for(int i = 0; i < 22; i++) {
		   for(int j = 0; j < 62; j++) {
			   System.out.print(screen[i][j]);
		   }
		   System.out.println();
	   }  
   }
   public MultiLinkedList selection(boolean select,Object[][] screen,int px,int py) throws InterruptedException {
	   if(select) {
		   selection.addCategory(1);
		   Thread.sleep(750);
			   if(rkey == KeyEvent.VK_F2) {
				   select = false;
				   screen(screen);
				   direction = 0;
				   return selection;
			   }
			   if(rkey==KeyEvent.VK_LEFT && 0<px-1) {//1
		      		 Object letter = screen[py][px];
		      		 selection.addItem(1, letter);
		      		 px--;
		      		 screen[py][px+1] = letter;
		      		cnt.setCursorPosition(px-1, py);
		      		att0=cn.getTextAttributes();
		      	     cn.setTextAttributes(att1);
		      	     System.out.print(letter);
		      		cn.setTextAttributes(att0);
		      		 cnt.setCursorPosition(px, py);
		      		 keypr= 0;
		      		 direction = 1;
		      		Thread.sleep(750);
		      	 }
		           if(rkey==KeyEvent.VK_RIGHT && px+1 < 60) {//2
		          	 Object letter = screen[py][px];
		          	 selection.addItem(1, letter);
		      		 px++;
		      		 screen[py][px-1] = letter;
		      		cnt.setCursorPosition(px-1, py);
		      		att0=cn.getTextAttributes();
		      	     cn.setTextAttributes(att1);
		      	     System.out.print(letter);
		      		cn.setTextAttributes(att0);
		      		 cnt.setCursorPosition(px, py);
		      		keypr= 0;
		      		direction = 2;
		      		Thread.sleep(750);
		           }
		           if(rkey==KeyEvent.VK_UP && 0<py-1 ) {//3
		          	 Object letter = screen[py][px];
		          	 selection.addItem(1, letter);
		      		 py--;
		      		 screen[py+1][px] = letter;
		      		cnt.setCursorPosition(px-1, py);
		      		att0=cn.getTextAttributes();
		      	     cn.setTextAttributes(att1);
		      	     System.out.print(letter);
		      		cn.setTextAttributes(att0);
		      		 cnt.setCursorPosition(px, py);
		      		 keypr= 0;
		      		direction = 3;
		      		Thread.sleep(750);
		           }
		           if(rkey==KeyEvent.VK_DOWN && py+1 < 21) {//4
		          	 Object letter = screen[py][px];
		          	 selection.addItem(1, letter);
		      		 py++;
		      		 screen[py-1][px] = letter;
		      		cnt.setCursorPosition(px-1, py);
		      		att0=cn.getTextAttributes();
		      	     cn.setTextAttributes(att1);
		      	     System.out.print(letter);
		      		cn.setTextAttributes(att0);
		      		 cnt.setCursorPosition(px, py);
		      		 keypr = 0;
		      		direction = 4;
		      		Thread.sleep(750);
		           }
		           keypr = 0;
	   }
	return selection;
   }
   Game() throws Exception {   // --- Contructor
       Object[][] screen = displayscreen();
    // ------ Standard code for keyboard and mouse 2 -------- Do not change -----
       tmlis=new TextMouseListener() {
          public void mouseClicked(TextMouseEvent arg0) {}
          public void mousePressed(TextMouseEvent arg0) {
             if(mousepr==0) {
                mousepr=1;
                mousex=arg0.getX();
                mousey=arg0.getY();
             }
          }
          public void mouseReleased(TextMouseEvent arg0) {}
       };
       cn.getTextWindow().addTextMouseListener(tmlis);
     
       klis=new KeyListener() {
          public void keyTyped(KeyEvent e) {}
          public void keyPressed(KeyEvent e) {
             if(keypr==0) {
                keypr=1;
                rkey=e.getKeyCode();
                rkeymod=e.getModifiersEx();
             }
          }
          public void keyReleased(KeyEvent e) {}
       };
       cn.getTextWindow().addKeyListener(klis);
       // --------------------------------------------------------------------------
       
       int curtype;
       curtype=cnt.getCursorType();   // default:2 (invisible)       0-1:visible
       cnt.setCursorType(1);
       cn.setTextAttributes(att0);
       
       int px=1,py=1;
       cnt.setCursorPosition(px, py);    
      while(true) {
         if(mousepr==1) {  // if mouse button pressed
            //cn.getTextWindow().output(mousex,mousey,'#');  // write a char to x,y position without changing cursor position
            px=mousex; py=mousey;
            
            mousepr=0;     // last action  
         }
         if(keypr==1|| select) {    // if keyboard button pressed
        	 if(select) {
        		 selection = selection(select,screen,px,py);
        	if(direction == 1) {
   				  px--;
   			  }
   			  if(direction == 2)
   				  px++;
   			  if(direction == 3)
   				  py--;
   			  if(direction == 4)
   				  py++;
   			cnt.setCursorPosition(px, py); 
        	 }
        	 if(rkey==KeyEvent.VK_LEFT  && select == false) {
        		 if(px==1 && py != 1) {
        			 px = 61;
        			 py--;
        			 cnt.setCursorPosition(px, py);
        		 }
        		 Object letter = screen[py][px];
        		 px--;
        		 screen[py][px+1] = letter;
        		 cn.getTextWindow().output(px+1,py,(char)letter);
        	 }
             if(rkey==KeyEvent.VK_RIGHT && select == false) {
            	 if(px == 61 && py != 21) {
            		 px = 0;
            		 py++;
            		 cnt.setCursorPosition(px, py);
            	 }
            	 Object letter = screen[py][px];
        		 px++;
        		 screen[py][px-1] = letter;
        		 cn.getTextWindow().output(px-1,py,(char)letter);
             }
             if(rkey==KeyEvent.VK_UP && 0<py-1 && select == false) {
            	 Object letter = screen[py][px];
        		 py--;
        		 screen[py+1][px] = letter;
        		 cn.getTextWindow().output(px,py+1,(char)letter);
             }
             if(rkey==KeyEvent.VK_DOWN && py+1 < 21&& select == false) {
            	 Object letter = screen[py][px];
        		 py++;
        		 screen[py-1][px] = letter;
        		 cn.getTextWindow().output(px,py-1,(char)letter);
             }
            
            char rckey=(char)rkey;
            if(rckey=='%' || rckey=='\'' || rckey=='&' || rckey=='(') {    // test without using VK (Virtual Keycode)
                cnt.setCursorPosition(px, py);
                //System.out.print('|'); 
              }
              else {
            	  if(rkey == KeyEvent.VK_SPACE) {
            		  Queue space = new Queue(2000);
            		  for(int i = 1; i < 21; i++) {
            			  for(int j = 1; j < 60; j++) {
            				  if(i == 1 && j == 1) {
            					  while(i != py) {
                					  i++;
                				  }
                				  while(j != px) {
                					  j++;
                				  }
            				  }
            				  space.Enqueue(screen[i][j]);
            			  }
            		  }
            		  screen[py][px] = (Object) ' ';
            		  System.out.print(screen[py][px]);
            		  if(px != 60) {
            			  px++;
            		  }
            		  else {
            			  px = 1;
            			  py++;
            		  }
            		  for(int i = 1; i < 21; i++) {
            			  for(int j = 1; j < 60; j++) {
            				  if(i == 1 && j == 1) {
            					  while(i != py) {
                					  i++;
                				  }
                				  while(j != px) {
                					  j++;
                				  }
            				  }
            				  screen[i][j] = space.peek();
            				  cn.getTextWindow().output(j,i,(char) space.Dequeue());
            			  }
            		  }
            		  screen(screen);
            		  cnt.setCursorPosition(px, py);
            	  }
            	  if(rkey == KeyEvent.VK_CAPS_LOCK) {
            		  if(capslock == 1)
            			  capslock = 0;
            		  else
            			  capslock = 1;
            	  }
            	  if(rkey == KeyEvent.VK_PAGE_UP) {
            		  up--;
            		  if(upflag == false) {
            			  for(int i = 1; i<21; i++) {
            				  page.addCategory(i);
            				  for(int j = 1; j < 60; j++) {
            					  page.addItem(i, screen[i][j]);
            				  }
            			  }
            			  page = page.pageup(page);
            			  row--;
            			  beautifuldisplay(page,screen,px,py);
            			  upflag = true;
            		  }
            		  else {
            			  int counter = 1;
                		  upflag = true;
                		  page = page.pageup(page);
                		  Category temp = page.head;
                		  Item temp2 = temp.getRight();
                		  for(int i = row; i < row + 20; i++) {
                    		  for(int a = 1; a < 60; a++) {
                    			  while(true) {
                    				  if(temp == null)
                    					  break;
                    				  if(page.searchboolean(i)) {
                    					  while(temp!=null&&(int) temp.getCategory() != i) {
                    						  temp = temp.getDown(); 
                    						  if(temp!= null)
                    							  temp2 = temp.getRight();
                    					  }
                    					  temp2 = null;
                    					  int counter1 = 1;
                    					  while(counter1 != 60) {
                    						  page.addItem(i, screen[counter][a]);
                    						  counter1++;
                    					  }
                    					  break;
                    				  }
                    				  else {
                    					  page.addCategory(i);
                    				  }
                    			  } 
                    		  }
                    		  counter++;
                		  }
                		  row--;
                		  beautifuldisplay(page,screen,px,py);
                		  if(py != 61) {
                			  py++;
                		  }
                		  cn.getTextWindow().setCursorPosition(px,py);
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_PAGE_DOWN) {
            		  down++;
            		  if(downflag == false) {
            			  for(int i = 1; i<21; i++) {
            				  page.addCategory(i);
            				  for(int j = 1; j < 60; j++) {
            					  page.addItem(i, screen[i][j]);
            				  }
            			  }
            			  page = page.pagedown(page);
            			  row++;
            			  beautifuldisplay(page,screen,px,py);
            			  downflag = true;
            		  }
            		  else {
            			  int counter = 1;
                		  downflag = true;
                		  Category temp = page.head;
                		  Item temp2 = temp.getRight();
                		  page = page.pagedown(page);
                		  for(int i = row; i < row + 20; i++) {
                    		  for(int a = 1; a < 60; a++) {
                    			  while(true) {
                    				  if(temp == null)
                    					  break;
                    				  if(page.searchboolean(i)) {
                    					  while(temp!= null && (int) temp.getCategory() != i) {
                    						  temp = temp.getDown();
                    						  if(temp != null)
                    							  temp2 = temp.getRight();
                    					  }
                    					  temp2 = null;
                    					  int counter1 = 1;
                    					  while(counter1 != 60) {
                    						  page.addItem(i, screen[counter][a]);
                    						  counter1++;
                    					  }
                    					  break;
                    				  }
                    				  else {
                    					  page.addCategory(i);
                    				  }
                    			  }
                    		  }
                    		  counter++;
                		  }
                		  row++;
                		  beautifuldisplay(page,screen,px,py);
                		  if(py != 1) {
                			  py--;
                		  }
                		  cn.getTextWindow().setCursorPosition(px,py);
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_HOME) {
            		  px = 1;
            		  cn.getTextWindow().setCursorPosition(px,py);
            	  }
            	  if(rkey == KeyEvent.VK_END) {
            		  int i = 60;
            		  while(screen[py][i] == (Object) ' ') {
            			  i--;
            		  }
            		  px = i + 1;
            		  cn.getTextWindow().setCursorPosition(px,py);
            	  }
            	  if(rkey == KeyEvent.VK_ENTER) {
            		  if(px == 61 && py != 21) {
            			  px = 1;
            			  py++;
            			  cnt.setCursorPosition(px, py);
            		  }
            		  if(py != 20) {
            			  int gx = px;
            			  Queue enter = new Queue(60);
            			  while(true) {//kopyalama
            				  enter.Enqueue(screen[py][px]);
            				  px++;
            				  if(px == 60) {
            					  py++;
            					  break;
            				  }
            			  }
            			  for(int i = gx; i < 60; i++) {//screen
            				  screen[py-1][i] = (Object) ' ';
            				  cn.getTextWindow().output(i,py-1,' ');
            			  }
            			  int coord= 1;
            			  while(true) {//yazdýrma
            				  screen[py][coord] = (Object) enter.peek();
            				  cn.getTextWindow().output(coord,py,(char)enter.Dequeue());
            				  coord++;
            				  if(enter.isEmpty()|| coord == 60)
            					  break;
            			  }
            			  px = 1;
            			  cn.getTextWindow().setCursorPosition(1,py);
            		  }
            		  screen(screen);
            		  cn.getTextWindow().setCursorPosition(px,py);
            	  }
            	  if(rkey == KeyEvent.VK_INSERT) {
            		  int gx = px, gy = py;
            		  if(!mod) {
            			  mode = "Insert";
               		   cn.getTextWindow().setCursorPosition(65,15);
               		   System.out.println("                       ");
               		   cn.getTextWindow().setCursorPosition(65,15);
               		   System.out.println("Mode : " + mode);
               		   mod = true;
               		   px = gx;py = gy;
               		   cn.getTextWindow().setCursorPosition(px,py);
            		  }
            		  else {
            			  mode = "Overwrite";
            			  mod = false;
            			  cn.getTextWindow().setCursorPosition(65,15);
                  		   System.out.println("                       ");
                  		   cn.getTextWindow().setCursorPosition(65,15);
                  		   System.out.println("Mode : " + mode);
                  		 px = gx;py = gy;
                 		   cn.getTextWindow().setCursorPosition(px,py);
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_F1) {
            		  select = true; 
            		  cn.getTextWindow().setCursorPosition(px,py);
            	  }
            	  if(rkey == KeyEvent.VK_F2 || select) {
            		  if(select) {
            			  keypr = 0;
            			  selection = selection(select,screen,px,py);
           			  if(direction == 1) {
            				  px--;
            			  }
            			  if(direction == 2)
            				  px++;
            			  if(direction == 3)
            				  py--;
            			  if(direction == 4)
            				  py++;
            			  if(rkey == KeyEvent.VK_F2) {
            				  select = false;
            				  direction = 0;
            			  } 
            		  }
            		  cn.getTextWindow().setCursorPosition(px,py);
            	  }
            	  if(rkey == KeyEvent.VK_F3) {
            		  select = false;
            		  direction = 0;
            		  if(selected == null) {
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("You didn't select any letter.");
            			  Thread.sleep(3000);
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("                             ");
            		  }
            		  else {
            			   selected = selection.getSelected(selection);px --;
            			  int size = selected.length(),counter = 0;
            			  while(counter != size) {
            				  screen = cut(screen,px,py);
            				  counter++;
            				  if(px == 1) {
            					  if(py != 1) {
            						  py--;
            						  px = 60;
            					  }
            				  }
            				  else {
            					  px--;
            				  }
            			  }
            			  if(px!=1)
        					  px--;
        				  else {
        					  if(py != 1) {
        						  py--;
            					  px = 60;
        					  }
        				  }
            			  cnt.setCursorPosition(px,py);
            			  screen(screen);
            			  rkey = 0;
            			  keypr = 0;
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_F4) {
            		  select = false;
            		  direction = 0;
            		  if(selected == null) {
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("You didn't select any letter.");
            			  Thread.sleep(3000);
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("                             ");
            		  }
            		  else {
            			   selected = selection.getSelected(selection);
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_F5) {
            		  if(selected.isEmpty()) {
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("You didn't select any letter.");
            			  Thread.sleep(3000);
            			  cnt.setCursorPosition(30,1);
            			  System.out.println("                             ");
            		  }
            		  else {
            			  int size = selected.length();
            			  for(int i = 0; i < size; i++) {
            				  cnt.setCursorPosition(px,py);
            				  screen[py][px] = selected.charAt(i);
            				  System.out.print(selected.charAt(i));
            				  if(px != 61) {
            					  px++;
            				  }
            				  else {
            					  px = 1;
            					  py++;
            				  }
            			  }
            		  }
            		  selected = "";
            		  selection.head.setRight(null);
            	  }
            	  if(rkey == KeyEvent.VK_F6) {
            		  cnt.setCursorPosition(65,19);
            		  System.out.print("Find : ");
            		  letter = sc.next();
            		  if(upflag == false && downflag == false) {
            			  for(int i = 1; i < 21;i++) {
            				  page.addCategory(i);
            				  for(int j = 1; j < 60;j++) {
            					  page.addItem(i, screen[i][j]);
            				  }
            			  }
            		  }
            		  page.searchitem(page, letter);
            		  int foundcount = page.getFoundCount();
            		  categorynumber = page.getcategorynumber();
            		  itemnumber = page.getItemnumber();
            		  int num = categorynumber[0];
            		  if(upflag || downflag) {
            			  for(int i = 0; i < foundcount ; i++) {
            				  if(i != 0) {
            					  categorynumber[i] = 2-row + (categorynumber[i] - num);
            				  }
            				  else
            					  categorynumber[i] = 2-row;
                		  }
            		  }
            		  if(foundcount>=1) {
            			  att0=cn.getTextAttributes();
   			      	      cn.setTextAttributes(att1);
   			      	      int length = 0;
			      	      while(length != letter.toString().length()) {
			      	          cnt.setCursorPosition(itemnumber[0]-length,categorynumber[0]);
			      	          System.out.print(screen[categorynumber[0]][itemnumber[0]-length]);
			      	    	  length++;
			      	      }
   			      	      cn.setTextAttributes(att0);
            		  }
            		  cnt.setCursorPosition(65,19);
            		  System.out.print(foundcount + " found.       ");
            	  }
            	  if(rkey == KeyEvent.VK_F7) {
            		  cnt.setCursorPosition(65,21);
             		  System.out.print("Replace with : ");
             		  replace = sc.next();
            		  F7(screen,px,py);
            	  }
            	  if(rkey == KeyEvent.VK_F8) {
            		  if(categorynumber.length>=next) {
            			  screen(screen);
            			  att0=cn.getTextAttributes();
   			      	      cn.setTextAttributes(att1);
   			      	      int length = 0;
   			      	      while(length != letter.toString().length()) {
   			      	    	  cnt.setCursorPosition(itemnumber[next]-length,categorynumber[next]);
			      	          System.out.print(screen[categorynumber[next]][itemnumber[next]-length]);
   			      	    	  length++;
   			      	      }
   			      	      cn.setTextAttributes(att0);
   			      	      next++;
            		  }
         	      }
            	  if(rkey == KeyEvent.VK_F9) {
            		  int i = 1,j=1,xx = px,yy = py;boolean flag = false;
            		  cnt.setCursorPosition(65,13);
            		  alignment = "Align left";
            		  System.out.print("Alignment :  " + alignment);
            		  while(i!= 21) {
            			  if(screen[i][1] != (Object) ' ') {
            				i++;  
            			  }
            			  else {
            				  j = 1;
            				  while(true) {
            					  if(screen[i][j] != (Object) ' ') {
            						  flag = true;
            						  break;
            					  }
            					  else {
            						  if(j == 60)
            						  {
            							  flag = true;
            							  break;
            						  }
            						  flag = false;
            						  j++;
            					  }
            				  }
            				  if(flag) {
            					  Queue column = new Queue(60-j);
            					  int a = j;
            					  while(a!= 60) {
            						  column.Enqueue(screen[i][a]);
                					  a++;
            					  }
            					  int counter =1;
                				  while(counter != 60) {
                					  screen[i][counter] = (Object)' ';
                					  counter++;
                				  }
                				  screen(screen);
                				  for(int k =1; k <a; k++) {
                					  if(column.isEmpty())
                						  continue;
                					  cnt.setCursorPosition(k,i);
                					  screen[i][k] = column.peek();
                					  System.out.print(column.Dequeue());
                				  }  
            				  }
            			  }
            			  i++;
            		  }
            		  px = 61-xx;
            		  py = yy;
            		  cnt.setCursorPosition(px, py);
            	  }
            	  
            	  if(rkey == KeyEvent.VK_ALT) {
            		  int i = 1,j=60;boolean flag = false;
            		  cnt.setCursorPosition(65,13);
            		  alignment = "Align Right";
            		  System.out.print("Alignment :  " + alignment);
            		  while(i!=21) {
            			  if(screen[i][60] != (Object) ' ') {
            				  i++;
            			  }
            			  else {
            				  j = 60;
            				  while(true) {
            					  if(screen[i][j] != (Object) ' ') {
            						  flag = true;
            						  break;
            					  }
            					  else {
            						  if(j == 1)
            							  break;
            						  flag = false;
            						  j--;
            					  }
            				  }
            				  if(flag) {
            					  Queue column = new Queue(j);
                				  int a = 1;
                				  while(a!= j+1) {
                					  column.Enqueue(screen[i][a]);
                					  a++;
                				  }
                				  int counter =1;
                				  while(counter != 60) {
                					  screen[i][counter] = (Object)' ';
                					  counter++;
                				  }
                				  for(int k =60-a+1; k <60; k++) {
                					  if(column.isEmpty())
                						  continue;
                					  cnt.setCursorPosition(k,i);
                					  screen[i][k] = column.peek();
                					  System.out.print(column.Dequeue());
                				  }  
            				  }
            			  }
            			  i++;
            		  }
            		  screen(screen);
            	  }
            	  if(rkey == KeyEvent.VK_F10) {
            		  int sx = px, sy = py,counter = 1,emptycounter = 0,wordcounter = 0,counter2 = 1;
            		  while(counter != 61) {
            			  if(screen[py][counter] == (Object) ' ' && counter<50) {
            				  emptycounter++;
            			  }
            			  else if(screen[py][counter] != (Object) ' ') {
            				  wordcounter++;
            			  }
            			  counter++;
            		  }
            		  counter = 1;
            		  int counter1=0;
            		  if(emptycounter + wordcounter < 60 && emptycounter<15) {
            			  while(emptycounter != 1) {
            				  while(counter != 61) {
            					  if(counter >= 59) {
                    				  break;
                    			  }
                    			  if(screen[py][counter] == (Object) ' ') {
                    				  Queue justify = new Queue(60-counter2);counter1 = counter;
                    				  while(counter1 != 59 && screen[py][counter1] != (Object) '|') {
                    					  if(justify.isFull())
                    						  break;
                    					  counter1++;
                    					  justify.Enqueue(screen[py][counter1]);
                    				  }
                    				  counter++;
                    				  screen[py][counter] = (Object) ' ';
                    				  cn.getTextWindow().output(counter,py,(char)' ');
                    				  counter++;
                    				  counter2 = counter;
                    				  counter1 = counter;
                    				  while(counter!=59) {
                    					  if(justify.isEmpty())
                    						  break;
                    					  screen[py][counter] = justify.peek();
                    					  cn.getTextWindow().output(counter,py,(char)justify.Dequeue());
                    					  counter++;
                    				  }
                    				  emptycounter--;
                    				  counter = counter2;
                    				  if(emptycounter == 0) {
                    					  emptycounter = 1;
                    					  break;
                    				  }
                    					  
                    			  }
                    			  else if(counter != 61) {
                    				  counter++;
                    				  counter2++;
                    			  }
                    		  }
            			  }
            		  }
            		  cn.getTextWindow().output(61,py,(char)'|');
            		  py++;
            		  px = 1;
            		  cnt.setCursorPosition(px, py);
            		  screen(screen);
            	  }
            	  if(rkey == KeyEvent.VK_F11) {
            		  int hx = px, hy = py;
            		  Scanner sc = new Scanner(new File("loadscreen.txt"));
            		  while(sc.hasNextLine()) {
            			  for(int i = 1; i < 21 ; i++) {
            				  String line = sc.nextLine();
            				  for(int j =1; j < 60; j++) {
            					  screen[i][j] = line.charAt(j-1);
            				  }
            			  }
            			  screen(screen);
            			  px = hx;
            			  py = hy;
            			  cnt.setCursorPosition(px,py);
            		  }
            	  }
            	  if(rkey == KeyEvent.VK_F12) {
            		  BufferedWriter bw = new BufferedWriter(new FileWriter("loadscreen.txt"));
            		  for(int i = 1; i < 21; i++) {
        				  for(int j = 1; j < 60; j++) {
        					  bw.write(screen[i][j].toString());
        				  }
        				  bw.write("\n");
        			  }
            		  bw.close();
            	}
                if(rckey>='0' && rckey<='9') {
                	if(px == 61) {
             		   py++;
             		   px=1;
             		   cnt.setCursorPosition(px, py);
             	   }
                	if(mod) {//insert
             		   if(0<px && px < 62 && 0<py && py < 22) {
                 		   screen[py][px] = rckey; 
                 		  cn.getTextWindow().output(px,py,(char)screen[py][px]);
                     	   px++;
                     	  cnt.setCursorPosition(px,py);
             		   }
             		   }
             	   else {
             		   if(screen[py][px] == (Object) ' ') {
             			   if(Character.isUpperCase(rckey)) {
             				  capslock = 1;
             				   screen[py][px] = rckey;
             				  cn.getTextWindow().output(px,py,(char)screen[py][px]);
             			   }
             			   else {
             				   capslock = 0;
             				  screen[py][px] = rckey;
             				 cn.getTextWindow().output(px,py,(char)screen[py][px]);
             			   }
             			  if(px != 59) {
            				   px++; 
            			   }
            			   else {
            				   px = 1;
            				   py++;
            			   }
            			  cnt.setCursorPosition(px,py); 
             		   }
             		   else {
             			   int bx = px, by = py;
             			   Queue right = new Queue(60);
             			   int kx = px, ky = py;
             			   while(screen[ky][kx] != (Object) ' ') {
             				   right.Enqueue(screen[ky][kx]);
             				   kx++;
             			   }
             			   screen[py][px] = rckey;
             			   cn.getTextWindow().output(px,py,(char)screen[py][px]);
             			   px++;
             			   while(!right.isEmpty()) {
             				   screen[py][px] = right.peek();
             				   cn.getTextWindow().output(px,py,(char)right.Dequeue());
             				   px++;
             			   }
             			   px--;
             			   if(bx != 60) {
             				   px = bx+1; 
                 			   py = by;
             			   }
             			   else {
             				   px = 1;
             				   py++;
             			   }
             			  cnt.setCursorPosition(px,py); 
             		   }
             	   }
                }
               if(rckey>='A' && rckey<='Z') {
            	   if(px == 61) {
            		   int wx = 0 ,counter = 60;
            		   Queue end = new Queue(60);
            		   while(true) {
            			   	if(screen[py][counter] == (Object) ' ') {
            			   		wx = counter;
            			   		while(counter != 60) {
            			   			counter++;
            			   			end.Enqueue(screen[py][counter]);
            			   		}
            			   		while(wx != 61) {
            			   			cnt.setCursorPosition(wx, py);
            			   			screen[py][wx] = (Object) ' ';
            			   			System.out.print(' ');
            			   			wx++;
            			   		}
            			   		break;
            			   	}
            			   	else {
            			   		if(counter == 1)
            			   			break;
            			   		counter--;
            			   	}
            		   }
            		   py++;
            		   px=1;
            		   screen(screen);
            		   cnt.setCursorPosition(px, py);
            		   while(!end.isEmpty()) {
            			   screen[py][px] = end.peek();
            			   System.out.print(end.Dequeue());
            			   px++;
            		   } 
            	   }
            	   if(mod) {//insert
            		   if(0<px && px < 62 && 0<py && py < 22) {
            			   if(capslock == 1) {
            				   screen[py][px] =Character.toUpperCase(rckey); 
                        	   px++; 
            			   }
            			   else {
            				   screen[py][px] =Character.toLowerCase(rckey); 
                        	   px++;
            			   }
            		   }
            		   }
            	   else {
            		   if(screen[py][px] == (Object) ' ') {
            			   if(capslock == 1) {
            				   screen[py][px] = Character.toUpperCase(rckey);
                 			   px++;
            			   }
            			   else {
            				   screen[py][px] = Character.toLowerCase(rckey);
                 			   px++;
            			   }
            		   }
            		   else {
            			   if(capslock == 1) {
            				   int bx = px, by = py;
                			   Queue right = new Queue(60);
                			   int kx = px, ky = py;
                			   while(screen[ky][kx] != (Object) ' ') {
                				   right.Enqueue(Character.toUpperCase((char)screen[ky][kx]));
                				   kx++;
                			   }
                			   screen[py][px] = Character.toUpperCase(rckey);
                			   cn.getTextWindow().output(px,py,(char)screen[py][px]);
                			   px++;
                			   while(!right.isEmpty()) {
                				   screen[py][px] = right.peek();
                				   cn.getTextWindow().output(px,py,(char)right.Dequeue());
                				   px++;
                			   }
                			   px--; 
                			   if(bx != 61) {
                				   px = bx+1; 
                    			   py = by;
                			   }
                			   else {
                				   px = 1;
                				   py++;
                			   }
                			   cnt.setCursorPosition(px,py); 
            			   }
            			   else {
            				   int bx = px, by = py;
                			   Queue right = new Queue(60);
                			   int kx = px, ky = py;
                			   while(screen[ky][kx] != (Object) ' ') {
                				   right.Enqueue(Character.toLowerCase((char)screen[ky][kx]));
                				   kx++;
                			   }
                			   screen[py][px] = Character.toLowerCase(rckey);
                			   cn.getTextWindow().output(px,py,(char)screen[py][px]);
                			   px++;
                			   while(!right.isEmpty()) {
                				   screen[py][px] = right.peek();
                				   cn.getTextWindow().output(px,py,(char)right.Dequeue());
                				   px++;
                			   }
                			   px--; 
                			   if(bx != 61) {
                				   px = bx+1; 
                    			   py = by;
                			   }
                			   else {
                				   px = 1;
                				   py++;
                			   }
                			   cnt.setCursorPosition(px,py); 
            			   }
            			   
            		   }
            	   }
                  if(((rkeymod & KeyEvent.SHIFT_DOWN_MASK) > 0) || capslock==1) {
                	  //System.out.print(rckey);
                  }
                  //else System.out.print((char)(rckey+32));//
                  screen(screen);
                }
                if((rkeymod & KeyEvent.SHIFT_DOWN_MASK) == 0) {
                  if(rckey=='.' || rckey==',' || rckey=='-') {
                	  if(px == 59) {
               		   py++;
               		   px=1;
               		   cnt.setCursorPosition(px, py);
               	   }
                	  if(mod) {//insert
               		   if(0<px && px < 62 && 0<py && py < 22) {
               			screen[py][px] = rckey; 
               		    cn.getTextWindow().output(px,py,(char)screen[py][px]);
                   	    px++;
                   	    cnt.setCursorPosition(px,py);
               		   }
               		   }
               	   else {
               		   if(screen[py][px] == (Object) ' ') {
               			   screen[py][px] = rckey;
               			   px++;
               			cnt.setCursorPosition(px-1,py);
               			cn.getTextWindow().output((char)screen[py][px-1]);
               		   }
               		   else {
               			   int bx = px, by = py;
               			   Queue right = new Queue(60);
               			   int kx = px, ky = py;
               			   while(screen[ky][kx] != (Object) ' ') {
               				   right.Enqueue(screen[ky][kx]);
               				   kx++;
               			   }
               			   screen[py][px] = rckey;
               			   cn.getTextWindow().output(px,py,(char)screen[py][px]);
               			   px++;
               			   while(!right.isEmpty()) {
               				   screen[py][px] = right.peek();
               				   cn.getTextWindow().output(px,py,(char)right.Dequeue());
               				   px++;
               			   }
               			   px--; 
               			   if(bx != 59) {
               				   px = bx+1; 
                   			   py = by;
               			   }
               			   else {
               				   px = 1;
               				   py++;
               			   }
               			   cnt.setCursorPosition(px,py);
               		   }
               	   }
                  }
                }
                else {
                    if(rckey=='.') {
                    	capslock = 0;
                    	screen[py][px] = (Object) ':';
                    	cn.getTextWindow().output(px,py,(char)screen[py][px]);
                    	if(px != 59)
                    		px++;
                    	else {
                    		px = 1;
                    		py++;
                    	}
                    	cn.getTextWindow().setCursorPosition(px,py);
                    }
                    if(rckey==',') {
                    	capslock = 0;
                    	screen[py][px] = (Object) ';';
                    	cn.getTextWindow().output(px,py,(char)screen[py][px]);
                    	if(px != 59)
                    		px++;
                    	else {
                    		px = 1;
                    		py++;
                    	}
                    	cn.getTextWindow().setCursorPosition(px,py);
                    }
                  }
              }
              if(rkey == KeyEvent.VK_BACK_SPACE) {
            	  screen = backspace(screen,px,py);
            	  px--;
              }
              if(rkey == KeyEvent.VK_DELETE) {
            	  Queue delete = new Queue(2000);
            	  for(int i = 1; i < 21; i++) {
            		  for(int j = 1; j < 60;  j++) {
            			  if(i == 1 && j == 1) {
            				  while(i != py) {
             					  i++;
             				  }
             				  while(j != px + 1) {
             					  j++;
             				  }
            			  }
         				  delete.Enqueue(screen[i][j]);
            		  }
            	  }
            	  for(int i = 1;i < 21; i++) {
            		  for(int j = 1; j < 60; j++) {
            			  if(delete.isEmpty())
            				  break;
            			  if(i == 1 && j == 1) {
            				  while(i != py) {
             					  i++;
             				  }
             				  while(j != px) {
             					  j++;
             				  }
            			  }
            			  screen[i][j] = delete.peek();
            			  cn.getTextWindow().output(j,i,(char)delete.Dequeue());
            		  }
            	  }
            	  screen(screen);
            	  cn.getTextWindow().setCursorPosition(px,py);
            } 
              keypr=0;    // last action  
           }
           Thread.sleep(20);   
        } //end of game loop
        
     }
  }