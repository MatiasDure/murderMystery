import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.sound.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class projectGame extends PApplet {



//declaring screens
Screen[] screens;
Screen startScreen;
Screen explanationScreen1;
Screen explanationScreen2;
Screen explanationScreen3;
Screen creditScreen;
Screen respawnScreen;
Screen entranceScreen;
Screen upstairHallScreen;
Screen kitchenScreen;
Screen basementScreen;
Screen masterBedScreen;
Screen atticHallScreen;
Screen atticScreen;
Screen hallScreen;
Screen livingRoomScreen;
Screen chooseMurdererScreen;
Screen wonScreen;
Screen lostScreen;
Screen howToPlay;

//declaring items 
Item[] items;
Item butlerEntree;
Item maidEntree;
Item brotherEntree;
Item wifeEntree;
Item driedFlower;
Item morphine;
Item screwDriver;
Item keyAttic;
Item keyBasement;
Item keyMasterBedroom;
Item letter;
Item wifePortrait;
Item brotherPortrait;
Item maidPortrait;
Item butlerPortrait;
Item pot;
Item pot2;

//declaring NPCs
Npc wife;
Npc brother;
Npc butler;
Npc maid;
Npc cat;

//declaring player
Player mainCharacter;

//declaring Inventory 
Inventory inventory;

//Game font
PFont pixelFont;

//Game sounds
SoundFile collectingItem;
SoundFile mainMenuSound;
SoundFile restSound;

//fading 
SoundFile currentTrack = null;
SoundFile nextTrack = null;
float targetVolume = 1;
boolean fading = false;

int currentScreen;

PImage cursorImg;

public void setup() {

  
  //fullScreen(P2D);
  
  //Font
  pixelFont = createFont("pixelFont.ttf",72);
  textFont(pixelFont);
  
  //sounds
  collectingItem = new SoundFile(this, "collectingItem.wav");
  restSound = new SoundFile(this, "restSound.mp3");
  mainMenuSound = new SoundFile(this, "mainMenuSound.mp3");
  
  collectingItem.amp(0.7f);

  //cursor
  cursorImg = loadImage("crosshair.png");
  cursor(cursorImg);

  currentScreen = 0;
  mainCharacter = new Player();
  
  float dialogueBoxX = width/2;
  float dialogueBoxY = height - 80;
  PImage dialogueBoxImg = loadImage("dialogueBox.png");
  
  //initializing npcs
  wife = new Npc(width/2-38,height/2+60,300,300,"wife",new TextBox(dialogueBoxX,dialogueBoxY,1400,220,40,"",dialogueBoxImg),new String[]{"Agatha: I can’t let anyone see the letters","Agatha: I’m glad I hid them before the maid entered.","Agatha: Why is it so cold all of a sudden? I should tell the maid to do something about that."},new Animation(loadImage("npcs/wifeIdle.png"),6,1));
  brother = new Npc(width-500,height/2+30,330,330,"brother",new TextBox(dialogueBoxX,dialogueBoxY,1400,220,40,"",dialogueBoxImg),new String[]{"Edward: The perfect time to return","Edward: Did someone leave the window open?"},new Animation(loadImage("npcs/brotherIdle.png"),5,1));
  butler = new Npc(width/2-98,height/2+30,330,330,"butler",new TextBox(dialogueBoxX,dialogueBoxY,1400,220,40,"",dialogueBoxImg),new String[]{"James: I hope I hid the spare attic key well","James: Let’s hope the house stays safe, Master kyle….","James: The wind sure is strong today"},new Animation(loadImage("npcs/butlerIdle.png"),4,1));
  maid = new Npc(width/2-56,height/2+60,300,300,"maid",new TextBox(dialogueBoxX,dialogueBoxY,1400,220,40,"",dialogueBoxImg),new String[]{"Sarah*shivers*: I should get firewood for the fireplace.","Sarah: I wonder where Kyle put the spare key to his room...","Sarah: I should burn my diary, that’s the best way…"},new Animation(loadImage("npcs/maidIdle.png"),4,1));
  cat = new Npc(320,height/2-280,250,250,"cat",new TextBox(dialogueBoxX,dialogueBoxY,1400,220,40,"",dialogueBoxImg),new String[]{"Olie: Meow! Meow!"},new Animation(loadImage("npcs/catIdle.png"),6,1));
  
  //initializing inventory
  inventory = new Inventory();
  
  int buttonXOffset = 50;
  int buttonYOffset = 150;
  
  //item temp imgs variables
  String emptyEntree = "items/emptyEntree.png";
  String ButlerEntree = "items/butlerEntree.png";
  String MaidEntree = "items/maidEntree.png";
  String BrotherEntree = "items/brotherEntree.png";
  String WifeEntree = "items/wifeEntree.png";
  String DriedFlower = "items/driedFlower.png";
  String Morphine = "items/morphine.png";
  //String ScrewDriver = "items/screwDriver.png";
  String KeyAttic = "items/keyAttic.png";
  String KeyBasement = "items/keyBasement.png";
  String KeyMasterBedroom = "items/keyMasterBedroom.png";
  String Letter = "items/letter.png";
  String WifePortrait = "items/wifePortrait.png";
  String BrotherPortrait = "items/brotherPortrait.png";
  String MaidPortrait = "items/maidPortrait.png";
  String ButlerPortrait = "items/butlerPortrait.png";
  String Pot = "items/pot.png";
  String Pot2 = "items/pot2.png";
  
  //initializing the items
  butlerEntree = new Item(emptyEntree,width-110,300,50,50,true,false,new InventoryItem(ButlerEntree,inventory.posX+30,60 + inventory.posY,true,"butlerEntree"));
  maidEntree = new Item(emptyEntree,width/2 + 350, height/2-30,50,50,true,false,new InventoryItem(MaidEntree,100 + inventory.posX,60 + inventory.posY,true,"maidEntree"));
  brotherEntree = new Item(emptyEntree,width/2-350, height-300, 50,50,true,false,new InventoryItem(BrotherEntree,170 + inventory.posX,60 + inventory.posY,true,"brotherEntree"));
  wifeEntree = new Item(emptyEntree,width-250, height-220,50,50,true,false,new InventoryItem(WifeEntree,240 + inventory.posX,60 + inventory.posY,true,"wifeEntree"));
  driedFlower = new Item(DriedFlower,width-280, height - 220,50,50,false,false,new InventoryItem(DriedFlower,inventory.posX+310,60 + inventory.posY,false,"driedFlower"));
  morphine = new Item(Morphine,width/2-280, height/2+110,50,50,false,false,new InventoryItem(Morphine,30 + inventory.posX,150 + inventory.posY,false,"morphine"));
  //screwDriver = new Item(ScrewDriver,90,height/2+175,50,50,false,false,new InventoryItem(ScrewDriver,100 + inventory.posX,150 + inventory.posY,false,"screwDriver"));
  keyAttic = new Item(KeyAttic,width/2-200, height/2+310,50,50,false,true,new InventoryItem(KeyAttic,170 + inventory.posX,150 + inventory.posY,false,"keyAttic"));
  keyBasement = new Item(KeyBasement,230,250,50,50,false,false,new InventoryItem(KeyBasement,240 + inventory.posX,150 + inventory.posY,false,"keyBasement"));
  keyMasterBedroom = new Item(KeyMasterBedroom, width-60, height/2+50,50,50,false,true,new InventoryItem(KeyMasterBedroom,100 + inventory.posX,150 + inventory.posY,false,"keyMasterBedroom"));
  letter = new Item(Letter,width-100, height/2-170,50,50,false,false,new InventoryItem(Letter,310+ inventory.posX,150 + inventory.posY,false,"letter"));
  wifePortrait = new Item(WifePortrait,550, 260,361,625);
  brotherPortrait = new Item(BrotherPortrait,width -860, 260,361,625);
  maidPortrait = new Item(MaidPortrait,80, 260,361,625);
  butlerPortrait = new Item(ButlerPortrait,width-380, 260,361,625);
  pot = new Item(Pot,width/2-160, 810,76,88);
  pot2 = new Item(Pot2,width-100, height/2+50,76,88);
  
  
  items = new Item[]{butlerEntree, maidEntree, brotherEntree, wifeEntree, driedFlower, morphine, keyMasterBedroom, keyAttic, keyBasement, letter};
  
  //initializing all 11 screens
  startScreen = new Screen(loadImage("backgrounds/mainMenu.png"), "Main Menu", new TextBox[]{new TextBox(width/2,height*0.33334f,375,120,75,"START",loadImage("button.png")),new TextBox(width/2,height*0.66667f,375,120,75,"CREDITS",loadImage("button.png")),new TextBox(width/2,height/2,375,120,75,"HOW TO PLAY",loadImage("button.png"))},false);
  creditScreen = new Screen(loadImage("backgrounds/credits.png"), "Credits",false);
  howToPlay = new Screen(loadImage("backgrounds/howToPlay.png"), "User Interface",false);
  explanationScreen1 = new Screen(loadImage("backgrounds/explanation1.png"), "Cutscene", new Arrow[]{new Arrow(width-buttonXOffset-45,height/2-70,70,70,3)},false);
  explanationScreen2 = new Screen(loadImage("backgrounds/explanation2.png"), "Cutscene", new Arrow[]{new Arrow(width-buttonXOffset-45,height/2-70,70,70,4)},false);
  explanationScreen3 = new Screen(loadImage("backgrounds/npcLeaving.png"), "Cutscene", new Arrow[]{new Arrow(width-buttonXOffset-45,height/2-70,70,70,5)},false);
  respawnScreen = new Screen(loadImage("backgrounds/respawnScreen.png"), "Cutscene", new Arrow[]{new Arrow(width-buttonXOffset-45,height/2-70,70,70,6)},false);
  entranceScreen = new Screen(loadImage("backgrounds/entrance.png"), "Entrance", new Arrow[]{new Arrow(width-325, height/2,200,350, 8), new Arrow(width/2-100, 350,200,50,7)},new Npc[]{butler},false);
  upstairHallScreen = new Screen(loadImage("backgrounds/upstairsHall.png"), "Upstairs Hall", new Arrow[]{new Arrow(width/2-200, height-190,400,25, 6), new Arrow(width-50,150,25,750,11), new Arrow(buttonXOffset,150,25,750,13), new Arrow(width/2-160, height/2+50,300,150,10)},false);
  kitchenScreen = new Screen(loadImage("backgrounds/kitchen.png"), "Kitchen", new Arrow[]{new Arrow(150,height/2,200,350, 6), new Arrow(width-50,150,25,750,9)},new Item[]{keyBasement},new Npc[]{maid},false);
  basementScreen = new Screen(loadImage("backgrounds/basement.png"), "Basement", new Arrow[]{new Arrow(buttonXOffset,150,25,750, 8)},new Item[]{maidEntree,driedFlower},new Npc[]{cat},true);
  masterBedScreen = new Screen(loadImage("backgrounds/bedroom.png"), "Master Bedroom", new Arrow[]{new Arrow(buttonXOffset, 150,25,750,7)},new Item[]{letter,morphine,wifeEntree},new Npc[]{wife},true);
  atticHallScreen = new Screen(loadImage("backgrounds/atticHall.png"), "Attic Hall", new Arrow[]{new Arrow(width-75, buttonYOffset,50,220, 12), new Arrow(buttonXOffset,150,25,750,7)},false);
  atticScreen = new Screen(loadImage("backgrounds/attic.png"), "Attic", new Arrow[]{new Arrow(width-330,height-400,150,220,11)},new Item[]{butlerEntree,keyMasterBedroom,pot2},true);
  hallScreen = new Screen(loadImage("backgrounds/leftHall.png"), "Hall", new Arrow[]{new Arrow(width-buttonXOffset, 150,25,750, 7), new Arrow(buttonXOffset, 150,25,750,14)}, new Item[]{keyAttic,pot},false);
  livingRoomScreen = new Screen(loadImage("backgrounds/livingRoom.png"), "Living Room", new Arrow[]{new Arrow(width-buttonXOffset,150,25,750,13)},new Item[]{brotherEntree},new Npc[]{brother},false);
  chooseMurdererScreen = new Screen(loadImage("backgrounds/choosingMurderer.png"),new Item[]{wifePortrait,brotherPortrait,maidPortrait,butlerPortrait},false);
  wonScreen = new Screen(loadImage("backgrounds/wonScreen.png"), "You Won",false);
  lostScreen = new Screen(loadImage("backgrounds/lostScreen.png"), "You Lost",false);

  screens = new Screen[]{startScreen, creditScreen, explanationScreen1, explanationScreen2, explanationScreen3, 
                        respawnScreen, entranceScreen, upstairHallScreen, kitchenScreen, basementScreen, masterBedScreen, 
                        atticHallScreen, atticScreen, hallScreen, livingRoomScreen, chooseMurdererScreen, wonScreen, lostScreen, howToPlay};
                        
  startFade(mainMenuSound);
}

public void draw()
{
  if(fading) doFade();
  if(currentScreen == 0 || currentScreen == 18) startFade(mainMenuSound);
  else if(currentScreen == 6) startFade(restSound);

  for(int i=0; i<screens.length; i++)
  {
     if(i == currentScreen)
     {
       screens[i].update(); 
       break;
     } 
  }
   if(currentScreen > 5 && currentScreen < 15) mainCharacter.update();
}

public void mouseReleased()
{
  //arrows in the cutscenes and credit screen
  if(currentScreen > 0 && currentScreen < 6)
  {
      try
      {
        for(int i=0; i<screens[currentScreen].arrows.length ; i++)
        {
          screens[currentScreen].arrows[i].clicked = false;
        }
      }
      catch(Exception e){}
  }
  
  //All UI in screens where the player can interact with objects
  if(currentScreen > 5)
  {
    mainCharacter.isMoving = false; 
    if(inventory.clicked) inventory.clicked = false;
    
    //diary entrees
    for(int i=0; i<inventory.diariesInInventory.size(); i++)
    {
       if(inventory.diariesInInventory.get(i).clicked)
       {
         inventory.diariesInInventory.get(i).clicked = false;
       }
    }
    
    //buttons to close diary entrees
    for(int i=0; i<inventory.diariesInInventory.size(); i++)
    {
       if(inventory.diariesInInventory.get(i).closeButton.clicked)
       {
         inventory.diariesInInventory.get(i).closeButton.clicked = false;
       }
    }
    if(screens[currentScreen].chooseButton.clicked) screens[currentScreen].chooseButton.clicked = false;
  }
  
}

public void keyReleased()
{
   //npcs dialogue text
  if(wife.clicked) wife.clicked = false;
  if(maid.clicked) maid.clicked = false;
  if(brother.clicked) brother.clicked = false;
  if(butler.clicked) butler.clicked = false;
  if(cat.clicked) cat.clicked = false;
}

public void startFade(SoundFile pNextTrack)
{
  if(nextTrack == pNextTrack) return;
  currentTrack = nextTrack;
  nextTrack = pNextTrack;
  fading = true;
  targetVolume = 0.5f - targetVolume;
  pNextTrack.amp(targetVolume);
  pNextTrack.loop();
}

public void doFade()
{
  targetVolume += 1/50.0f;
  if (currentTrack != null) currentTrack.amp(constrain (0.5f-targetVolume, 0, 0.5f));
  if (nextTrack != null) nextTrack.amp(constrain (targetVolume, 0, 0.5f));
  fading = targetVolume < 0.5f;
  
  if (!fading && currentTrack != null) {
    currentTrack.stop();
  }
}
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
  
  public void update()
  {
    pushMatrix();
    scale(-1,1);
    image(images[currentImg],-posX,posY, npcImgWidth,npcImgHeight);
    if(frameCount % 15 == 0) next();
    popMatrix();
  }
  
  public void next()
  {
    currentImg++; 
    if(currentImg >= images.length) currentImg = 0;
  }
}
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
  
 public void update()
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


public boolean hoverPlayer()
{
  if(mainCharacter.posX-40+mainCharacter.imageWidth-165 >= xPos &&
     mainCharacter.posX-30 <= xPos+arrowWidth &&
     mainCharacter.posY+20 <= yPos+arrowHeight &&
     mainCharacter.posY+mainCharacter.imageHeight+40 >= yPos ) return true;
     
     return false;
}

public boolean hoverMouse()
{
 if(mouseX >= xPos &&
    mouseX <= xPos+arrowWidth &&
    mouseY >= yPos &&
    mouseY <= yPos+arrowHeight) return true;
    return false;
}
  
}
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
  
  public void update()
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
  
  public boolean overButton()
  {
    if(buttons[0].hover() || buttons[1].hover()) return true;
    return false;
  }
  
  public boolean overInventoryBox()
  {
    if(mouseX >= posX &&
       mouseX <= posX+inventoryWidth &&
       mouseY >= posY &&
       mouseY <= posY+inventoryHeight && open) return true;
    return false;
  }
  
}
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
 
 public void update()
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
 
   public boolean hover()
   {
     if(mouseX >= posX+10 &&
           mouseX <= posX+invItemWidth-10 &&
           mouseY >= posY &&
           mouseY <= posY+invItemHeight) return true;
         return false;
   }
   
}
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
    
  
  public void update()
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
  
  public boolean playerHover()
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

  public boolean mouseHover()
  {
    if(mouseX >= xPos  &&
       mouseX <= xPos+itemWidth-10 &&
       mouseY >= yPos-10 &&
       mouseY <= yPos+itemHeight-10) return true;
   return false; 
  }
}
class Npc
{
  float posX;
  float posY;
  int npcWidth;
  int npcHeight;
  String npcImgName;
  TextBox dialogue;
  Animation npcAnimation;
  boolean clicked;
  String[] futureText;
  int timesClickedOn = 0;

  Npc(float pPosX, float pPosY, int pNpcWidth, int pNpcHeight, String pNpcImgName, TextBox pDialogue, String[] pFutureText,  Animation pNpcAnimation)
  {
     posX = pPosX;
     posY = pPosY;
     npcWidth = pNpcWidth;
     npcHeight = pNpcHeight;
     npcImgName = pNpcImgName;
     dialogue = pDialogue;
     futureText = pFutureText;
     npcAnimation = pNpcAnimation;
     npcAnimation.posX = pPosX;
     npcAnimation.posY = pPosY;
     npcAnimation.npcImgWidth = pNpcWidth;
     npcAnimation.npcImgHeight = pNpcHeight;
  }
  
  public void update()
  {
    try
    {
      npcAnimation.update();
    }
    catch(Exception e)
    {
      rect(posX,posY,npcWidth,npcHeight);
    }
    
    if(hover() && timesClickedOn < futureText.length)
    {
      if(keyPressed && (key == 'r' || key == 'R') && !clicked)
      {
       clicked = true;
       dialogue.boxText = futureText[timesClickedOn];
       timesClickedOn++;
      }
      text("Press 'r' to read character's dialogue ",width/2,height/2);
    }
    dialogue.update();
  }
  
  public boolean hover()
  {
    if(mouseX >= posX-200 &&
       mouseX <= posX+npcWidth-400 &&
       mouseY >= posY &&
       mouseY <= posY+npcHeight) return true;
    return false; 
  }
  
}
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
  
  public void update() { 

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
      
      posX = lerp(posX, translateX, .04f);
      posY = lerp(posY, translateY, .04f);
    } 
    
    tint(255,(sin(millis()/300.0f)+1.2f)*255);
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
  
  public void nextImg()
  {
    currentImg++;
  }
  
}
class Screen {
  PImage backgroundImg;
  PImage screenNameBckg;
  String screen;
  Arrow[] arrows;
  Item[] items;
  TextBox[] textboxes;
  TextBox menu;
  TextBox chooseButton;
  Npc[] npcs = null;
  boolean locked;

  Screen(PImage pBackground, String pScreen, Arrow[] pArrows, Item[] pItems, Npc[] pNpcs, boolean pLocked){
    backgroundImg = pBackground;
    screen = pScreen;
    arrows = pArrows;
    items = pItems;
    npcs = pNpcs;
    locked = pLocked;
    screenNameBckg = loadImage("roomName.png");
    //back to menu buttons
    menu = new TextBox(100,80,130,75,33,"MENU",loadImage("menuButton.png"));
    //choosing murderer button
    chooseButton = new TextBox(width-130,height-80,139,90,loadImage("chooseMurdererIcon.png"));
    
  }
  
  Screen(PImage pBackground, String pScreen, Arrow[] pArrows, Npc[] pNpcs, boolean pLocked)
  {
    this(pBackground,pScreen,pArrows,null,pNpcs, pLocked);
  }
  
  Screen(PImage pBackground, String pScreen, Arrow[] pArrows, boolean pLocked)
  {
    this(pBackground,pScreen,pArrows,null,null, pLocked);
  }
  Screen(PImage pBackground, String pScreen, Arrow[] pArrows, Item[] pItems, boolean pLocked)
  {
    this(pBackground,pScreen,pArrows,pItems,null,pLocked);
  }
  
  Screen(PImage pBackground, String pScreen, TextBox[] pTextboxes, boolean pLocked)
  {
    
    this(pBackground,pScreen,null,null,null,pLocked);
    textboxes = pTextboxes;
  }
  
  Screen(PImage pBackground, String pScreen, boolean pLocked)
  {
    this(pBackground,pScreen,null,null,null,pLocked);
  }
  
  Screen(PImage pBackground, Item[] pItems, boolean pLocked)
  {
    this(pBackground,null,null,pItems,null,pLocked);
  }
  
  public void update() 
  {
    
      image(backgroundImg,0,0,width,height);
      //Drawing the title of screens
      if(currentScreen < 15)
      {
        textSize(44);
        textAlign(CENTER,CENTER);
        image(screenNameBckg, width/2-145,30,290,100);
        text(screen, width/2, 75); 
      }  
      
      //hovering animations and screen transition for the buttons in the main menu
      if(currentScreen == 0)
      {
        for(int i=0; i<textboxes.length; i++)
        {
           //hovering over start screen's buttons
           if(textboxes[i].hover())
           {
             if(textboxes[i].boxWidth != 300)
              {
                textboxes[i].boxWidth = 450;
                textboxes[i].boxHeight = 144;
                textboxes[i].textSize = 90;
              }
              if(mousePressed && !textboxes[i].clicked)
              {
                if(i == 0) currentScreen = 2;
                else if(i == 1) currentScreen = 1;
                else currentScreen = 18;
              }
           }
           else if(textboxes[i].boxWidth != 250)
            {
              textboxes[i].boxWidth = 375;
              textboxes[i].boxHeight = 120;
              textboxes[i].textSize = 75;
            }
            
            textboxes[i].update(); 
          }
        }
      
      
      //update npcs and items to screen
      if(npcs != null)for(int i=0; i<npcs.length; i++)npcs[i].update();
      if(items != null) for(int i=0; i<items.length;i++)items[i].update();
      
       //updating hit boxes teleporters to screen
      if(arrows != null)
      {
        for(int i=0; i<arrows.length; i++)
        {
          arrows[i].update();
        }
      }
      
       if(currentScreen == 15 && wifePortrait.mouseHover() && mousePressed)currentScreen = 16;
       else if(currentScreen == 15 && (brotherPortrait.mouseHover()||maidPortrait.mouseHover()||butlerPortrait.mouseHover()) && mousePressed)currentScreen = 17;
      
      //Happens in the user playable screens
      if(currentScreen > 5 && currentScreen < 15)
      { 
        //hover effects for when inventory is not open
          if(!inventory.open)
          {
            if(inventory.buttons[0].hover())
            {
              if(inventory.buttons[0].boxWidth != 165)
              {
                inventory.buttons[0].boxWidth = 165;
                inventory.buttons[0].boxHeight = 135;
              }
            }
            else
            {
              if(inventory.buttons[0].boxWidth != 150)
              {
                inventory.buttons[0].boxWidth = 150;
                inventory.buttons[0].boxHeight = 115;
              }
            }
          }
          //hover effectes for when inventory is open
          else
          {
            if(inventory.buttons[1].hover())
            {
                if(inventory.buttons[1].boxWidth != 40)
                {
                  inventory.buttons[1].boxWidth = 40;
                  inventory.buttons[1].boxHeight = 40;
                  inventory.buttons[1].textSize = 43;
                }
            }
            else
            {
               if(inventory.buttons[1].boxWidth != 35)
               {
                 inventory.buttons[1].boxWidth = 35;
                 inventory.buttons[1].boxHeight = 35;
                 inventory.buttons[1].textSize = 39;
               }
            }
          }
          inventory.update();
          
          //takes you to the choose your murderer screen 
          if(chooseButton.hover())
          {
            if(chooseButton.boxWidth != 149)
            {
              chooseButton.boxWidth = 149;
              chooseButton.boxHeight = 100;
            }
            if(mousePressed && !chooseButton.clicked)
            {
              currentScreen = 15;
              chooseButton.clicked = true;
            }
          }
          else
          {
            if(chooseButton.boxWidth != 139)
            {
              chooseButton.boxWidth = 139;
              chooseButton.boxHeight = 90;
            }
          }
          chooseButton.update();
      }
      
      //hover animations for the main menu buttons in every screen
      if(currentScreen > 0)
      {
        if(menu.hover())
        {
          if(menu.boxWidth != 150)
          {
            menu.boxWidth = 150;
            menu.boxHeight = 95;
            menu.textSize = 38;
          }
          if(mousePressed && !menu.clicked) currentScreen = 0;
        }
        else
        {
           if(menu.boxWidth != 130)
           {
             menu.boxWidth = 130;
             menu.boxHeight = 75;
             menu.textSize = 33;
           }
        }
        menu.update();
      }
  }
  
}
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
  
  public void update()
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
  
  public boolean hover()
  {
    if(mouseX >= xPos-boxWidth/2 &&
       mouseX <= xPos+boxWidth/2 &&
       mouseY >= yPos-boxHeight/2 &&
       mouseY <= yPos+boxHeight/2) return true;
     return false;
  }
}
  public void settings() {  size(1920,1080,P2D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "projectGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
