class Player
{
  boolean isMoving;
  float posX = width/2;
  float posY = height/2+30;
  float translateX = width/2; 
  float translateY = height/2+30;
  float nextScreenPosX;
  float nextScreenPosY;
  int playerHeight = 300;
  int playerWidth = 300;
  int spriteWidth;
  int spriteHeight;
  int imageWidth;
  int imageHeight;
  int spritePosX;
  int spritePosY;
  int spriteColumns = 3;
  int spriteRows = 2;
  int currentImg = 0;
  PImage charImg;
  PImage[] images = new PImage[6];
  boolean left;
  
  Player()
  {
   charImg = loadImage("mainChar.png"); 
   spriteWidth = charImg.width;
   spriteHeight = charImg.height;
   imageWidth = spriteWidth/spriteColumns;
   imageHeight = spriteHeight/spriteRows;
   
   for(int i=0; i<images.length; i++)
   {
     spritePosX = i % spriteColumns*imageWidth;
     spritePosY = i / spriteColumns*imageHeight;
     images[i] = charImg.get(spritePosX,spritePosY,imageWidth,imageHeight);
   }
   
  }
  
  void update() { 

    if (mousePressed && !isMoving && !inventory.overButton() 
        && !inventory.overInventoryBox() && !pot.mouseHover() && !pot2.mouseHover())
    {
      translateX = mouseX - playerWidth/2 + 175;
      translateY = mouseY - playerHeight/2;
      if(translateY > 600) translateY = 600;
      else if(translateY < 135) translateY = 135; 
      isMoving = true;
    }
    
     pushMatrix();

     if(translateX - posX < 0){
       scale(-1,1);
       left = true;
     }
     else{
       scale(1,1);
       left = false;
     }

    if (posX != translateX && posY != translateY) 
    {
      
      posX = lerp(posX, translateX, .04);
      posY = lerp(posY, translateY, .04);
    } 
    
    tint(255,(sin(millis()/300.0)+1.2)*255);
    if(!left)
    {
      image(images[currentImg],posX-playerWidth/2,posY,playerWidth,playerHeight);
    }
    else
    {
      image(images[currentImg],-posX-playerWidth/2,posY,playerWidth,playerHeight);
    }
    tint(255,255);
    popMatrix();
    if(frameCount % 6 == 0) nextImg();
    if(currentImg > 5) currentImg = 0; 

  }
  
  void nextImg()
  {
    currentImg++;
  }
  
}
