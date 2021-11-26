class InventoryItem
{
 float posX;
 float posY;
 float zoomedPosX;
 float zoomedPosY;
 int invItemWidth = 50;
 int invItemHeight = 50;
 int zoomedInvWidth = 550;
 int zoomedInvHeight = 700;
 String identifier;
 PImage itemImg;
 boolean isClickable;
 boolean clicked;
 boolean diaryOpen;
 TextBox closeButton;
 
  InventoryItem(String pItemImg, float pPosX, float pPosY, boolean pIsClickable, String pIdentifier)
 {
   itemImg = loadImage(pItemImg);
   posX = pPosX;
   posY = pPosY;
   isClickable = pIsClickable;
   identifier = pIdentifier;
   if(isClickable)
   {
     zoomedPosX = width/2-zoomedInvWidth/2;
     zoomedPosY = height/2-zoomedInvHeight/2;
     closeButton = new TextBox(zoomedPosX + zoomedInvWidth-90,zoomedPosY + 84, 35,35,39,"X");
   }
 }
 
 void update()
 {
   if(isClickable && hover())
   {
      if(invItemWidth != 60)
      {
        invItemWidth = 60;
        invItemHeight = 60;
      }
      if(mousePressed && !clicked)
      {
         clicked = true; 
         diaryOpen = true;
      }
   }
   else if(isClickable)
   {
      if(invItemWidth != 50)
      {
        invItemWidth = 50;
        invItemHeight = 50;
      }
   }
   
   if(diaryOpen)
   {
         image(itemImg,zoomedPosX,zoomedPosY,zoomedInvWidth,zoomedInvHeight);
         if(closeButton.hover())
         {
            if(closeButton.boxWidth != 40)
            {
              closeButton.boxWidth = 40;
              closeButton.boxHeight = 40;
              closeButton.textSize = 43;
            }
            if(mousePressed && !closeButton.clicked)
            {
              mainCharacter.isMoving = true;
              diaryOpen = false;
              closeButton.clicked = true;
            }
          }
          else
          {
             if(closeButton.boxWidth != 35)
             {
               closeButton.boxWidth = 35;
               closeButton.boxHeight = 35;
               closeButton.textSize = 39;
             }
          }
          closeButton.update();
 
   }
   else
   {
        image(itemImg,posX,posY, invItemWidth, invItemHeight);
   }
   
 }
 
   boolean hover()
   {
     if(mouseX >= posX+10 &&
           mouseX <= posX+invItemWidth-10 &&
           mouseY >= posY &&
           mouseY <= posY+invItemHeight) return true;
         return false;
   }
   
}
