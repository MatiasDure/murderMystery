class TextBox
{
 float xPos;
 float yPos;
 int boxWidth;
 int boxHeight;
 int[] rgbColor = new int[3];
 String boxText;
 int textSize;
 boolean clicked;
 PImage boxImg = null;
  
  
  TextBox(float pXPos, float pYPos, int pBoxWidth, int pBoxHeight)
  {
    this(pXPos,pYPos,pBoxWidth,pBoxHeight,1,"",null);
  }
  
  TextBox(float pXPos, float pYPos, int pBoxWidth, int pBoxHeight, int pTextSize, String pBoxText)
  {
    this(pXPos,pYPos,pBoxWidth,pBoxHeight,pTextSize,pBoxText,null);
  }
  
    TextBox(float pXPos, float pYPos, int pBoxWidth, int pBoxHeight, PImage pBoxImg)
  {
    this(pXPos,pYPos,pBoxWidth,pBoxHeight,1,"",pBoxImg);
  }
  
  TextBox(float pXPos, float pYPos, int pBoxWidth, int pBoxHeight, int pTextSize, String pBoxText, PImage pBoxImg)
  {
    xPos = pXPos;
    yPos = pYPos;
    boxWidth = pBoxWidth;
    boxHeight = pBoxHeight;
    textSize = pTextSize;
    boxText = pBoxText;
    boxImg = pBoxImg;
  }
  
  void update()
  {
    
    noStroke();
    if(boxImg != null) image(boxImg,xPos-boxWidth/2,yPos-boxHeight/2,boxWidth,boxHeight);
    else
    {
      fill(rgbColor[0],rgbColor[1],rgbColor[2],170);  
      rect(xPos-boxWidth/2, yPos-boxHeight/2, boxWidth, boxHeight,7); 
    }
    if(boxText != null)
    {
      textAlign(CENTER,CENTER);
      textSize(textSize);
      fill(255);
      text(boxText,xPos+2,yPos-textSize/10);
    }
   
  }
  
  boolean hover()
  {
    if(mouseX >= xPos-boxWidth/2 &&
       mouseX <= xPos+boxWidth/2 &&
       mouseY >= yPos-boxHeight/2 &&
       mouseY <= yPos+boxHeight/2) return true;
     return false;
  }
}
