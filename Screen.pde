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
  
  void update() 
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
