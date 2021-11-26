class Animation
{
  PImage spriteImg;
  PImage[] images;
  float posX;
  float posY;
  int spriteColumns;
  int spriteRows;
  int spriteSheetWidth;
  int spriteSheetHeight;
  int imageWidth;
  int imageHeight;
  int npcImgWidth;
  int npcImgHeight;
  int spriteSheetX;
  int spriteSheetY;
  int currentImg = 0;
  
  Animation(PImage pSpriteImg, int pSpriteColumns, int pSpriteRows)
  {
    spriteImg = pSpriteImg;
    spriteColumns = pSpriteColumns;
    spriteRows = pSpriteRows;
    images = new PImage[spriteColumns*spriteRows];
    
    spriteSheetWidth = spriteImg.width;
    spriteSheetHeight = spriteImg.height;
    
    imageWidth = spriteSheetWidth/spriteColumns;
    imageHeight = spriteSheetHeight/spriteRows;
    
    
    for(int i=0; i < spriteColumns*spriteRows; i++)
    {
      spriteSheetX = i % spriteColumns*imageWidth;
      spriteSheetY = i / spriteColumns*imageHeight;
      images[i] = spriteImg.get(spriteSheetX, spriteSheetY, imageWidth,imageHeight);
    }
  }
  
  void update()
  {
    pushMatrix();
    scale(-1,1);
    image(images[currentImg],-posX,posY, npcImgWidth,npcImgHeight);
    if(frameCount % 15 == 0) next();
    popMatrix();
  }
  
  void next()
  {
    currentImg++; 
    if(currentImg >= images.length) currentImg = 0;
  }
}
