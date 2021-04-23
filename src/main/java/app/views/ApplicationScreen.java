package app.views;

import app.Game;
import app.ScreenOrchestrator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import network.Network;
import player.Player;
import projectCard.ProgramCard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ApplicationScreen extends AbstractScreen {
    private Game game;
    private final Skin skin = new Skin(Gdx.files.internal("src/assets/skin/plain-james/plain-james-ui.json"));
    private final int width;
    private final int height;
    private int gameWidth;
    private Player player;

    private Image cardSlotsMiddle;
    private Image cardSlotsBottom;
    private Image flagsImage;
    private Image dmgTokensImage;
    private Image lifeTokensImage;
    private final Image locked1Image = new Image(new Sprite(new Texture("src/assets/lockedLabel.png")));
    private final Image locked2Image = new Image(new Sprite(new Texture("src/assets/lockedLabel.png")));
    private final Image locked3Image = new Image(new Sprite(new Texture("src/assets/lockedLabel.png")));
    private final Image locked4Image = new Image(new Sprite(new Texture("src/assets/lockedLabel.png")));

    private final HashMap<Integer, Image> imageMap;

    private int flags;
    private int dmgTokens;
    private int lifeTokens;

    private ProgramCard[] chosenCards;
    private HashMap<Image, ProgramCard> cardImageProgramMap;
    private HashMap<ProgramCard, Image> cardProgramImageMap;
    private ArrayList<Point> chooseCardPos;

    public ApplicationScreen(ScreenOrchestrator screenOrchestrator) {
        super(screenOrchestrator);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        imageMap = new HashMap<>(4);
        imageMap.put(1, locked1Image);
        imageMap.put(2, locked2Image);
        imageMap.put(3, locked3Image);
        imageMap.put(4, locked4Image);
    }

    @Override
    public void show() {
        game = new Game();
        game.create();

        while (player == null) {
            try {
                player = game.getPlayers().get(Network.getMyId() - 1);
            } catch (Exception e) {
                System.out.println("Game not set up correct yet");
            }
        }

        flags = player.getFlagScore();
        dmgTokens = player.getNumDamageTokens();
        lifeTokens = player.getHealth();
        gameWidth = Gdx.graphics.getWidth() * 2 / 3;
        chosenCards = new ProgramCard[5];
        cardImageProgramMap = new HashMap<>(84);
        cardProgramImageMap = new HashMap<>(84);
        chooseCardPos = new ArrayList<>(5);

        initCardSlots();
        initButtons();
        initDamageTokens();
        initFlags();
        initLifeTokens();
        placeCards();
        initCurrentPlayer();

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Game side of screen
        Gdx.gl.glViewport(0, 0, gameWidth, Gdx.graphics.getHeight());
        game.render();
        player = game.getPlayers().get(Network.getMyId() - 1);

        if (flags != player.getFlagScore()) {
            updateFlags();
        }
        if (dmgTokens != player.getNumDamageTokens()) {
            updateDamageTokens();
        }
        if (lifeTokens != player.getHealth()) {
            updateLifeTokens();
        }

        //UI side of screen
        Gdx.gl.glViewport(0, 0, width, Gdx.graphics.getHeight());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Updates flag in GUI based on progress in the game
     */
    private void updateFlags() {
        Sprite sprite;
        switch (player.getFlagScore()) {
            case 0:
                sprite = new Sprite(new Texture("src/assets/playerGUI/flags/flags0.png"));
                break;
            case 1:
                sprite = new Sprite(new Texture("src/assets/playerGUI/flags/flags1.png"));
                break;
            case 2:
                sprite = new Sprite(new Texture("src/assets/playerGUI/flags/flags2.png"));
                break;
            default:
                sprite = new Sprite(new Texture("src/assets/playerGUI/flags/flags3.png"));
                break;
        }
        flagsImage = new Image(new SpriteDrawable(sprite));
        flagsImage.setPosition(gameWidth, height - (200 + (width - gameWidth) / 10));
        flagsImage.setWidth((width - gameWidth) / 2);
        flagsImage.setHeight((width - gameWidth) / 6);

        stage.addActor(flagsImage);

        flags = player.getFlagScore();
    }

    /**
     * Updates number of life tokens in GUI when player loses a life
     */
    private void updateLifeTokens() {
        Sprite sprite;

        for (Actor a : stage.getActors()) {
            if (a == locked1Image || a == locked2Image || a == locked3Image || a == locked4Image) {
                a.remove();
            }
        }

        switch (player.getHealth()) {
            case 0:
                sprite = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens3.png"));
                break;
            case 1:
                sprite = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens2.png"));
                break;
            case 2:
                sprite = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens1.png"));
                break;
            default:
                sprite = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens0.png"));
                break;
        }
        lifeTokensImage = new Image(new SpriteDrawable(sprite));
        lifeTokensImage.setPosition(gameWidth + ((width - gameWidth) / 2), height - (200 + (width - gameWidth) / 10));
        lifeTokensImage.setWidth((width - gameWidth) / 2);
        lifeTokensImage.setHeight((width - gameWidth) / 6);

        stage.addActor(lifeTokensImage);

        lifeTokens = player.getHealth();
    }

    /**
     * Updates damage tokens in GUI when the player takes damage
     */
    private void updateDamageTokens() {
        Sprite dmgTokensSprite;
        dmgTokens = player.getNumDamageTokens();

        int numOfLockedCards = Math.max(dmgTokens-5, 0);

        for (int i = 1; i < numOfLockedCards+1; i++) {
            int numOfCard = 5-i;
            imageMap.get(numOfCard).setPosition((float) chooseCardPos.get(numOfCard).getX(), (float) chooseCardPos.get(numOfCard).getY() + cardSlotsBottom.getHeight()/2);
            imageMap.get(numOfCard).setWidth((float) (width - gameWidth) / 5);
            imageMap.get(numOfCard).setHeight(imageMap.get(numOfCard).getWidth()/4);
            stage.addActor(imageMap.get(numOfCard));
            if (chosenCards[numOfCard] != null) {
                Image currCardImage = cardProgramImageMap.get(chosenCards[numOfCard]);
                currCardImage.setPosition(currCardImage.getOriginX(), currCardImage.getOriginY());
                chosenCards[numOfCard] = null;
            }
        }

        switch (player.getNumDamageTokens()) {
            case 10:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens10.png"));
                break;
            case 9:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens9.png"));
                break;
            case 8:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens8.png"));
                break;
            case 7:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens7.png"));
                break;
            case 6:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens6.png"));
                break;
            case 5:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens5.png"));
                break;
            case 4:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens4.png"));
                break;
            case 3:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens3.png"));
                break;
            case 2:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens2.png"));
                break;
            case 1:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens1.png"));
                break;
            default:
                dmgTokensSprite = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens0.png"));
                break;
        }

        dmgTokensImage = new Image(new SpriteDrawable(dmgTokensSprite));
        dmgTokensImage.setPosition(gameWidth, height - 150);
        dmgTokensImage.setWidth(width - gameWidth);
        dmgTokensImage.setHeight((width - gameWidth) / 10);

        stage.addActor(dmgTokensImage);
    }

    /**
     * If current player is hosting the server, deal cards to all players.
     * Get program cards from server and place these on screen with respective file in assets.
     */
    private void placeCards() {
        int cardWidth = (width - gameWidth) / 5;
        int cardHeight = cardWidth * 4 / 3;
        if (Network.hostingServer()) {
            Network.dealCardsToPlayers();
        }
        ArrayList<ProgramCard> cardsOnHand = Network.getCurrentProgramCards();
        for (int i = 0; i < 9 - player.getNumDamageTokens(); i++) {
            ProgramCard card = cardsOnHand.get(i);
            Sprite sprite = new Sprite(new Texture(card.getCardImagePath()));
            final Image cardImage = new Image(new SpriteDrawable(sprite));
            cardImage.setWidth(cardWidth);
            cardImage.setHeight(cardHeight);

            if (i < 5) {
                cardImage.setPosition(gameWidth + (cardWidth * i), cardHeight * 2 + 80);
                cardImage.setOrigin(gameWidth + (cardWidth * i), cardHeight * 2 + 80);
            } else {
                cardImage.setPosition(gameWidth + (cardWidth * (i - 5)), cardHeight + 80);
                cardImage.setOrigin(gameWidth + (cardWidth * (i - 5)), cardHeight + 80);
            }
            cardImage.addListener(new DragListener() {
                @Override
                public void drag(InputEvent event, float x, float y, int pointer) {
                    cardImage.moveBy(x - cardImage.getWidth() / 2, y - cardImage.getHeight() / 2);
                    cardImage.toFront();
                }

                @Override
                public void dragStop(InputEvent event, float x, float y, int pointer) {
                    super.dragStop(event, x, y, pointer);
                    dragDropCard(cardImage);
                }
            });
            stage.addActor(cardImage);
            cardImageProgramMap.put(cardImage, card);
            cardProgramImageMap.put(card, cardImage);
        }
    }

    /**
     * When a card is picked up and dragged by mouse, this method places it in the right place.
     */
    private void dragDropCard(Image cardImage) {
        float cardX = cardImage.getX() + cardImage.getWidth() / 2;
        float cardY = cardImage.getY() + cardImage.getHeight() / 2;
        ProgramCard programCard = cardImageProgramMap.get(cardImage);

        if ((cardY > cardSlotsMiddle.getY() && cardY < height && cardX > gameWidth && cardX < width) || cardX < gameWidth) {
            cardImage.setPosition(cardImage.getOriginX(), cardImage.getOriginY());
            for (int i = 0; i < 5; i++) {
                if (chosenCards[i] == programCard) {
                    chosenCards[i] = null;
                }
            }
            return;
        }
        int numberOfCardsToDraw = Math.min(10 - dmgTokens, 5);
        for (int i = 0; i < 5; i++) {
            if (cardX > chooseCardPos.get(i).getX() - cardImage.getWidth() / 2 && cardX < chooseCardPos.get(i).getX() + cardImage.getWidth()
                    && cardY > 0 && cardY < cardSlotsMiddle.getY()) {
                if (i >= numberOfCardsToDraw) {
                    cardImage.setPosition(cardImage.getOriginX(), cardImage.getOriginY());
                    return;
                }
                if (chosenCards[i] != null) {
                    ProgramCard prevProgramCard = chosenCards[i];
                    Image prevImage = cardProgramImageMap.get(prevProgramCard);
                    prevImage.setPosition(prevImage.getOriginX(), prevImage.getOriginY());
                    for (int j = 0; j < 5; j++) {
                        if (chosenCards[j] == programCard) {
                            prevImage.setPosition(chooseCardPos.get(j).x, chooseCardPos.get(j).y);
                            chosenCards[j] = prevProgramCard;
                            break;
                        }
                    }
                }
                else {
                    for (int j = 0; j < 5; j++) {
                        if (chosenCards[j] == programCard) {
                            chosenCards[j] = null;
                        }
                    }
                }

                cardImage.setPosition(chooseCardPos.get(i).x, chooseCardPos.get(i).y);
                chosenCards[i] = programCard;
                return;
            }
        }
    }

    /**
     * Initializes label to show which number the current player is.
     */
    private void initCurrentPlayer() {
        Label label = new Label("You are player " + player.getId(), skin);
        label.setPosition(gameWidth + (width - gameWidth) / 2 - label.getWidth() / 2, height - 30);
        stage.addActor(label);
    }

    /**
     * Initializes the cards slots where the program cards are placed
     */
    private void initCardSlots() {
        Sprite texture1 = new Sprite(new Texture("src/assets/playerGUI/cardSlots.jpg"));
        Sprite texture2 = new Sprite(new Texture("src/assets/playerGUI/cardSlotsNumbered.jpg"));
        Image cardSlotsTop = new Image(new SpriteDrawable(texture1));
        cardSlotsTop.setWidth(width - gameWidth);
        cardSlotsTop.setHeight((float) ((width - gameWidth) / 5 * 4 / 3));
        cardSlotsTop.setPosition(gameWidth, (float) ((width - gameWidth) / 5 * 4 / 3) * 2 + 80);
        cardSlotsTop.setColor(Color.LIGHT_GRAY);

        cardSlotsMiddle = new Image(new SpriteDrawable(texture1));
        cardSlotsMiddle.setWidth(width - gameWidth);
        cardSlotsMiddle.setHeight((float) ((width - gameWidth) / 5 * 4 / 3));
        cardSlotsMiddle.setPosition(gameWidth, (float) ((width - gameWidth) / 5 * 4 / 3) + 80);
        cardSlotsMiddle.setColor(Color.LIGHT_GRAY);

        cardSlotsBottom = new Image(new SpriteDrawable(texture2));
        cardSlotsBottom.setWidth(width - gameWidth);
        cardSlotsBottom.setHeight((float) ((width - gameWidth) / 5 * 4 / 3));
        cardSlotsBottom.setPosition(gameWidth, 80);

        for (int i = 0; i < 5; i++) {
            chooseCardPos.add(new Point(gameWidth + ((width - gameWidth) / 5 * i), 80));
        }

        stage.addActor(cardSlotsTop);
        stage.addActor(cardSlotsMiddle);
        stage.addActor(cardSlotsBottom);
    }

    /**
     * Initializes buttons
     * "Lock in" sends a signal to Game.java that the cards on hand is the ones that will be used
     */
    private void initButtons() {
        TextButton lockInButton = new TextButton("Lock in cards", skin);
        lockInButton.setPosition(width - ((width - gameWidth) / 3) - 10, 10);
        lockInButton.setWidth((width - gameWidth) / 3);
        lockInButton.setHeight(60);

        lockInButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for(ProgramCard pc : chosenCards) {
                    if(pc != null)
                        player.addProgramCard(pc);
                }
                player.setProgramCardDone();
                game.round();
            }
        });

        stage.addActor(lockInButton);
    }

    /**
     * Initializes picture to show the amount of damage tokens
     */
    private void initDamageTokens() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/damageTokens/damageTokens0.png"));
        dmgTokensImage = new Image(new SpriteDrawable(texture));
        dmgTokensImage.setPosition(gameWidth, height - 150);
        dmgTokensImage.setWidth(width - gameWidth);
        dmgTokensImage.setHeight((width - gameWidth) / 10);

        stage.addActor(dmgTokensImage);
    }

    /**
     * Initializes picture to show the amount of flags scored
     */
    private void initFlags() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/flags/flags0.png"));
        flagsImage = new Image(new SpriteDrawable(texture));
        flagsImage.setPosition(gameWidth, height - (200 + (width - gameWidth) / 10));
        flagsImage.setWidth((width - gameWidth) / 2);
        flagsImage.setHeight((width - gameWidth) / 6);

        stage.addActor(flagsImage);
    }

    /**
     * Initializes picture to show the amount of life tokens
     */
    private void initLifeTokens() {
        Sprite texture = new Sprite(new Texture("src/assets/playerGUI/lifeTokens/lifeTokens0.png"));
        lifeTokensImage = new Image(new SpriteDrawable(texture));
        lifeTokensImage.setPosition(gameWidth + ((width - gameWidth) / 2), height - (200 + (width - gameWidth) / 10));
        lifeTokensImage.setWidth((width - gameWidth) / 2);
        lifeTokensImage.setHeight((width - gameWidth) / 6);

        stage.addActor(lifeTokensImage);
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
