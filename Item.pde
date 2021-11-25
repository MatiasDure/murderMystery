class Item
{
  PImage itemImg;
  PImage otherImg;
  String itemPath;
  float xPos;
  float yPos;
  int itemWidth;
  int itemHeight;
  int normalWidth;
  int normalHeight;
  int hoveredWidth;
  int hoveredHeight;
  boolean itemFound;
  boolean diary;
  boolean collected;
  boolean hiddenItem;
  boolean hoverable = true;
  boolean clicked = false;
  InventoryItem itemInv;
  
  
  Item(String pItemPath ,float pXPos, float pYPos, int pItemWidth, int pItemHeight, boolean pDiary, boolean pHiddenItem, InventoryItem pItemInv)
  {
    itemPath = pItemPath;
    if(pItemPath == "items/pot.png" || pItemPath == "items/pot2.png") otherImg = loadImage("items/potBroken.png");
    itemImg = loadImage(pItemPath);
    xPos = pXPos;
    yPos = pYPos;
    itemWidth = pItemWidth;
    itemHeight = pItemHeight;
    diary = pDiary;
    hiddenItem = pHiddenItem;
    itemInv = pItemInv;
    normalWidth = pItemWidth;
    normalHeight = pItemHeight;
    hoveredWidth = pItemWidth + 10;
    hoveredHeight = pItemHeight + 10;
  }
  
  Item(String pItemPath ,float pXPos, float pYPos, int pItemWidth, int pItemHeight)
  {
   this(pItemPath,pXPos,pYPos,pItemWidth,pItemHeight,false,false,null); 
  }
    
  
  void update()
  {
    //checking if item is already collected and adding it to array
    if(playerHover() && !collected && !hiddenItem && itemPath != "items/pot.png" && itemPath != "items/pot2.png")
    {
      if(diary) inventory.diariesInInventory.add(itemInv); 
      else inventory.itemsInInventory.add(itemInv);

      collected = true;
      collectingItem.play();
    }
    
    //hovering over item with cursor if not collected yet
    if(!collected && !hiddenItem)
    {
      if(mouseHover() && hoverable)
      {
        if(itemWidth != hoveredWidth)
        {
          itemWidth = hoveredWidth;
          itemHeight = hoveredHeight;
        }
      }
      else
      {
        if(itemWidth != normalWidth)
        {
         itemWidth = normalWidth;
         itemHeight = normalHeight;
        }
      }
    }
    
    //displaying item if not collected
    if(itemPath == "items/pot.png" && mouseHover() && mousePressed && !clicked)
    {
      itemImg = otherImg;
      hoverable = false;
      keyAttic.hiddenItem = false;
      clicked = true;
    }
    if(itemPath == "items/pot2.png" && mouseHover() && mousePressed && !clicked)
    {
      itemImg = otherImg;
      hoverable = false;
      keyMasterBedroom.hiddenItem = false;
      clicked = true;
    }
    if(!collected && !hiddenItem) image(itemImg,xPos,yPos,itemWidth,itemHeight);
    
  }
  
  boolean playerHover()
  {
    if(mainCharacter.posX+mainCharacter.imageWidth-230 >= xPos &&
       mainCharacter.posX <= xPos+itemWidth+20 &&
       mainCharacter.posY+20 <= yPos+itemHeight &&
       mainCharacter.posY+mainCharacter.imageHeight+40 >= yPos )
       {
         itemFound = true; 
         return true;
       }
    return false;
  }

  boolean mouseHover()
  {
    if(mouseX >= xPos  &&
       mouseX <= xPos+itemWidth-10 &&
       mouseY >= yPos-10 &&
       mouseY <= yPos+itemHeight-10) return true;
   return false; 
  }
}
