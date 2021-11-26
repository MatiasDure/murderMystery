class Inventory
{
  PImage inventoryBag;
  PImage inventoryOpen;
  int inventoryWidth = 375;
  int inventoryHeight = 255;
  float posX = 1530;
  float posY = 30;
  ArrayList<InventoryItem> itemsInInventory;
  ArrayList<InventoryItem> diariesInInventory;
  TextBox[] buttons;
  boolean clicked;
  boolean open;
  boolean diaryIsOpen;
  boolean collectedBasementKey;
  boolean collectedAtticKey;
  boolean collectedMasterBedroomKey;
  
  Inventory()
  {
    itemsInInventory = new ArrayList();
    diariesInInventory = new ArrayList();
    inventoryBag = loadImage("inventoryButton.png");
    inventoryOpen = loadImage("inventoryOpen.png");
    buttons = new TextBox[]{new TextBox(width-100,80,100,84,inventoryBag),new TextBox(posX+inventoryWidth-30,posY + 30, 35,35,39,"X")};
  }
  
  void update()
  {
     overInventoryBox();
     if(open)
     {
         if(buttons[1].hover() && mousePressed && !clicked)
         {
           open = false;    
           clicked = true;
         }
         image(inventoryOpen,posX,posY, inventoryWidth,inventoryHeight);
         fill(0);
         buttons[1].update();
         fill(255);
         if(itemsInInventory.size() > 0)
         {
           for(int i=itemsInInventory.size()-1; i >= 0; i--)
           {
             itemsInInventory.get(i).update();
             
           }
         }
         if(diariesInInventory.size() > 0)
         {
           for(int i=diariesInInventory.size()-1; i >= 0; i--)
           {
             diariesInInventory.get(i).update();
           }
         }
     }
     else
     {
        buttons[0].update();
        for(int i=diariesInInventory.size()-1; i >= 0; i--)
        {
          if(diariesInInventory.get(i).diaryOpen)diariesInInventory.get(i).update();
        }
        if(buttons[0].hover() && mousePressed && !clicked)
        {
            for(int i=diariesInInventory.size()-1; i >= 0; i--)
            {
              if(diariesInInventory.get(i).identifier == "wifeEntree")
              {
                diariesInInventory.get(i).clicked = true;
                break; 
              }
            }
            open = true;
            clicked = true; 
        }
     }
     if(!collectedMasterBedroomKey || !collectedAtticKey || !collectedBasementKey)
     {
       for(int i=0; i<itemsInInventory.size();i++)
       {
        if(!collectedMasterBedroomKey && itemsInInventory.get(i).identifier == "keyMasterBedroom") collectedMasterBedroomKey = true;
        if(!collectedAtticKey && itemsInInventory.get(i).identifier == "keyAttic") collectedAtticKey = true;
        if(!collectedBasementKey && itemsInInventory.get(i).identifier == "keyBasement")collectedBasementKey = true;
       }
     }
     
     if(collectedAtticKey && screens[12].locked) screens[12].locked = false;
     if(collectedBasementKey && screens[9].locked) screens[9].locked = false;
     if(collectedMasterBedroomKey && screens[10].locked) screens[10].locked = false;
     
  }
  
  boolean overButton()
  {
    if(buttons[0].hover() || buttons[1].hover()) return true;
    return false;
  }
  
  boolean overInventoryBox()
  {
    if(mouseX >= posX &&
       mouseX <= posX+inventoryWidth &&
       mouseY >= posY &&
       mouseY <= posY+inventoryHeight && open) return true;
    return false;
  }
  
}
