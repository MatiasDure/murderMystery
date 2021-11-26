class Arrow
{
 float xPos;
 float yPos;
 int arrowWidth;
 int arrowHeight;
 PImage arrow;
 int transition;
 float sXPos = width/2;
 float sYPos = height/2 + 30;
 boolean clicked = false;
 int timer;
 
 
 Arrow(float pXPos, float pYPos, int pArrowWidth, int pArrowHeight,int pTransition)
 {
   
   transition = pTransition;
   xPos = pXPos;
   yPos = pYPos; 
   arrowWidth = pArrowWidth;
   arrowHeight = pArrowHeight;
   arrow = loadImage("arrow1.png");
 }  
  
 void update()
 {
   if(currentScreen < 6)
   {
     if(hoverMouse())
     {
       if(arrowWidth != 80)
       {
        arrowWidth = 80;
        arrowHeight = 80;
       }
     }
     else
     {
        if(arrowWidth != 70)
        {
          arrowWidth = 70;
          arrowHeight = 70;
        }
     }  
     image(arrow,xPos,yPos,arrowWidth,arrowHeight);
   }
   
    //teleporting to screens that are not locked
    if(hoverPlayer() && currentScreen >= 6 && (transition != 9 || !screens[9].locked) 
       && (transition != 10) && (transition != 12 || !screens[12].locked) )
    {
       currentScreen = transition; 
       mainCharacter.posX = sXPos;
       mainCharacter.posY = sYPos;
       mainCharacter.translateX = sXPos;
       mainCharacter.translateY = sYPos;
     }
     else if(hoverPlayer())
     {
       if(transition == 9 && screens[9].locked)text("You need to look around for the basement key", width/2,300); 
       else if(transition == 10 && screens[10].locked)text("Seems like Agatha locked herself in her room. Find the spare key", width/2,height-100); 
       else if(transition == 12 && screens[12].locked)text("The attic is locked, search for the missing key", width/2,height-100); 
     }
     
     if(transition == 10 && hoverMouse() && !screens[10].locked)
     {
         if(mousePressed && !clicked)
         {
           currentScreen = transition; 
           mainCharacter.posX = sXPos;
           mainCharacter.posY = sYPos;
           mainCharacter.translateX = sXPos;
           mainCharacter.translateY = sYPos;
         }
         text("Click to enter the bedroom",width/2,height-100);
     }
     
     
     if(currentScreen < 6 && hoverMouse() && mousePressed && !clicked)
     {
       
       for(int i=2; i<6; i++)
       {
        screens[i].arrows[0].clicked = true; 
       }
       if(currentScreen == 5) mainCharacter.isMoving = true;
       currentScreen = transition;
     }
 }


boolean hoverPlayer()
{
  if(mainCharacter.posX-40+mainCharacter.imageWidth-165 >= xPos &&
     mainCharacter.posX-30 <= xPos+arrowWidth &&
     mainCharacter.posY+20 <= yPos+arrowHeight &&
     mainCharacter.posY+mainCharacter.imageHeight+40 >= yPos ) return true;
     
     return false;
}

boolean hoverMouse()
{
 if(mouseX >= xPos &&
    mouseX <= xPos+arrowWidth &&
    mouseY >= yPos &&
    mouseY <= yPos+arrowHeight) return true;
    return false;
}
  
}
