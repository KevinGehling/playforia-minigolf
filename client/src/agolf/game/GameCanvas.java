package agolf.game;

import agolf.GameContainer;
import agolf.Seed;
import agolf.SynchronizedBool;
import com.aapeli.client.StringDraw;
import com.aapeli.tools.Tools;

import java.awt.*;
import java.awt.event.*;
import java.util.StringTokenizer;
import java.util.Vector;

public class GameCanvas extends GameBackgroundCanvas implements Runnable, MouseMotionListener, MouseListener, KeyListener {

    private static final double aDouble2798 = Math.sqrt(2.0D) / 2.0D;
    private static final int anInt2799 = (int) (6.0D * aDouble2798 + 0.5D);
    private static final Cursor cursorDefault = new Cursor(Cursor.DEFAULT_CURSOR);
    private static final Cursor cursorCrosshair = new Cursor(Cursor.CROSSHAIR_CURSOR);
    private static final Color colourAimLine = new Color(128, 0, 32);
    private static final Font aFont2803 = new Font("Dialog", 0, 10);
    private static final Color aColor2804 = Color.black;
    private static final Color aColor2805 = Color.white;
    private static final Color backgroundColour = new Color(19, 167, 19);
    private int gameState;
    private Image[] ballSprites;
    private int playerCount;
    private int anInt2810;
    private int anInt2811;
    private int currentPlayerID;
    private int mouseX;
    private int mouseY;
    private int mouseInputMode;
    private int anInt2816;
    private double startPointX;
    private double startPointY;
    private double aDouble2819;
    private double aDouble2820;
    private double[] multiballStartX;
    private double[] multiBallStartY;
    private Vector[] aVectorArray2823;
    private Vector[] aVectorArray2824;
    private short[][][] aShortArrayArrayArray2825;
    private double[] playerX;
    private double[] playerY;
    private double[] ballVelocityX;
    private double[] ballVelocityY;
    private boolean[] aBooleanArray2830;
    private SynchronizedBool[] aSynchronizedBoolArray2831;
    private boolean aBoolean2832;
    // what is displayed right next to the ball (hide, initials, full name, clan)
    private int showPlayerNameState;
    private boolean[] aBooleanArray2834;
    private String aString2835;
    private Seed aSeed_2836;
    private static int[] anIntArray2837 = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
    private static int anInt2838 = 2;
    private int anInt2839;
    private Image wholeCanvas;
    private Graphics graphics;
    private Thread aThread2842;
    private boolean aBoolean2843;

    protected GameCanvas(GameContainer var1, Image var2) {
        super(var1, var2);
        this.ballSprites = var1.spriteManager.getBalls();
        this.playerCount = this.currentPlayerID = this.mouseX = this.mouseY = -1;
        this.showPlayerNameState = 0;
        this.gameState = 0;
        this.anInt2839 = anInt2838;
    }

    public void update(Graphics g) {
        if (this.wholeCanvas == null) {
            this.wholeCanvas = this.createImage(735, 375);
            this.graphics = this.wholeCanvas.getGraphics();
        }

        super.update(this.graphics);
        if (this.gameState == 1 && this.mouseX > -1 && this.mouseY > -1) {
            double[] var2 = this.calculateVelocity(this.currentPlayerID, this.mouseX, this.mouseY);
            // cast to (int) with +0.5D <=> round to int
            int var3 = (int) (this.playerX[this.currentPlayerID] + 0.5D);
            int var4 = (int) (this.playerY[this.currentPlayerID] + 0.5D);
            int var5 = (int) (this.playerX[this.currentPlayerID] + var2[0] * 200.0D / 6.5D + 0.5D);
            int var6 = (int) (this.playerY[this.currentPlayerID] + var2[1] * 200.0D / 6.5D + 0.5D);
            this.graphics.setColor(colourAimLine);
            // draw user input (ball to mouse)
            if (this.mouseInputMode == 0) {
                this.graphics.drawLine(var3, var4, var5, var6);
                // special mouse mode (right mouse button)
            } else {
                int var7 = var5 - var3;
                int var8 = var6 - var4;
                this.method163(this.graphics, var3, var4, var7, var8);
                if (this.mouseInputMode == 1) {
                    var7 = -var7;
                    var8 = -var8;
                }

                int var9;
                if (this.mouseInputMode == 2) {
                    var9 = var7;
                    var7 = var8;
                    var8 = -var9;
                }

                if (this.mouseInputMode == 3) {
                    var9 = var7;
                    var7 = -var8;
                    var8 = var9;
                }

                this.graphics.drawLine(var3, var4, var3 + var7, var4 + var8);
            }
        }

        if (this.currentPlayerID > -1) {
            this.graphics.setFont(aFont2803);
            this.graphics.setColor(aColor2804);

            for (int player = 0; player < this.playerCount; ++player) {
                if (this.aBooleanArray2830[player] && player != this.currentPlayerID) {
                    this.drawBall(this.graphics, player, this.aSynchronizedBoolArray2831[player].get() ? 2.1666666666666665D : 0.0D);
                }
            }

            this.graphics.setColor(aColor2805);
            this.drawBall(this.graphics, this.currentPlayerID, this.aSynchronizedBoolArray2831[this.currentPlayerID].get() ? 2.1666666666666665D : 0.0D);
        }

        g.drawImage(this.wholeCanvas, 0, 0, this);
    }

    public void run() {
        Image var1 = this.createImage(735, 375);
        Image var2 = super.image;
        Graphics var3 = var1.getGraphics();
        Graphics var4 = this.getGraphics();
        var3.drawImage(var2, 0, 0, this);
        var4.drawImage(var1, 0, 0, this);
        int var5 = 0;
        int[] var6 = new int[this.playerCount];
        int[] var7 = new int[this.playerCount];
        double[] var8 = new double[this.playerCount];
        double[] var9 = new double[this.playerCount];
        double[] var10 = new double[this.playerCount];
        double[] var11 = new double[this.playerCount];
        double[] var12 = new double[this.playerCount];
        double[] var13 = new double[this.playerCount];
        double[] var14 = new double[this.playerCount];
        boolean[] var15 = new boolean[this.playerCount];
        boolean[] var16 = new boolean[this.playerCount];
        boolean[] var17 = new boolean[this.playerCount];
        int[] var18 = new int[this.playerCount];

        for (int var19 = 0; var19 < this.playerCount; ++var19) {
            var6[var19] = var7[var19] = 0;
            var8[var19] = var11[var19] = this.playerX[var19];
            var9[var19] = var12[var19] = this.playerY[var19];
            var15[var19] = var16[var19] = false;
            var10[var19] = this.aSynchronizedBoolArray2831[var19].get() ? 2.1666666666666665D : 0.0D;
            var17[var19] = false;
            var18[var19] = 0;
        }

        boolean var20 = false;
        boolean var21 = false;
        boolean var22 = false;
        boolean var23 = super.gameContainer.synchronizedTrackTestMode.get();
        if (var23) {
            var22 = super.gameContainer.gamePanel.maxFps();
        }

        int var24 = -1;
        byte var25 = 0;
        byte var26 = 0;
        byte var27 = 0;
        byte var28 = 0;
        byte var29 = 0;
        byte var30 = 0;
        byte var31 = 0;
        byte var32 = 0;
        byte var33 = 0;
        int var34 = 0;
        int var35 = 0;
        double var36 = 0.0D;
        this.aDouble2819 = this.aDouble2820 = 1.0D;
        int var38 = 0;

        do {
            long var39 = System.currentTimeMillis();

            for (int var41 = 0; var41 < this.playerCount; ++var41) {
                var13[var41] = this.playerX[var41];
                var14[var41] = this.playerY[var41];
            }

            int player;
            int var44;
            for (int var42 = 0; var42 < this.anInt2839; ++var42) {
                var24 = 0;

                for (player = 0; player < this.playerCount; ++player) {
                    if (this.aBooleanArray2830[player] && !this.aSynchronizedBoolArray2831[player].get()) {
                        for (var44 = 0; var44 < 10; ++var44) {
                            this.playerX[player] += this.ballVelocityX[player] * 0.1D;
                            this.playerY[player] += this.ballVelocityY[player] * 0.1D;
                            if (this.playerX[player] < 6.6D) {
                                this.playerX[player] = 6.6D;
                            }

                            if (this.playerX[player] >= 727.9D) {
                                this.playerX[player] = 727.9D;
                            }

                            if (this.playerY[player] < 6.6D) {
                                this.playerY[player] = 6.6D;
                            }

                            if (this.playerY[player] >= 367.9D) {
                                this.playerY[player] = 367.9D;
                            }

                            int var45;
                            if (this.anInt2811 == 1 && !var15[player] && !var16[player]) {
                                for (var45 = 0; var45 < this.playerCount; ++var45) {
                                    if (player != var45 && this.aBooleanArray2830[var45] && !this.aSynchronizedBoolArray2831[var45].get() && !var15[var45] && !var16[var45] && this.method147(player, var45)) {
                                        this.ballVelocityX[player] *= 0.75D;
                                        this.ballVelocityY[player] *= 0.75D;
                                        this.ballVelocityX[var45] *= 0.75D;
                                        this.ballVelocityY[var45] *= 0.75D;
                                        var24 = 0;
                                    }
                                }
                            }

                            var35 = (int) (this.playerX[player] + 0.5D);
                            var34 = (int) (this.playerY[player] + 0.5D);
                            var33 = super.isSolidArrayIThink[var35][var34];
                            var32 = super.isSolidArrayIThink[var35][var34 - 6];
                            var31 = super.isSolidArrayIThink[var35 + anInt2799][var34 - anInt2799];
                            var30 = super.isSolidArrayIThink[var35 + 6][var34];
                            var29 = super.isSolidArrayIThink[var35 + anInt2799][var34 + anInt2799];
                            var28 = super.isSolidArrayIThink[var35][var34 + 6];
                            var27 = super.isSolidArrayIThink[var35 - anInt2799][var34 + anInt2799];
                            var26 = super.isSolidArrayIThink[var35 - 6][var34];
                            var25 = super.isSolidArrayIThink[var35 - anInt2799][var34 - anInt2799];
                            if (var33 != 12 && var33 != 13) {
                                var21 = var33 == 14 || var33 == 15;
                            } else {
                                this.ballVelocityX[player] *= 0.97D;
                                this.ballVelocityY[player] *= 0.97D;
                                var21 = true;
                            }

                            var45 = 0;

                            for (int var46 = 32; var46 <= 38; var46 += 2) {
                                if (var32 == var46 || var31 == var46 || var30 == var46 || var29 == var46 || var28 == var46 || var27 == var46 || var26 == var46 || var25 == var46) {
                                    ++var45;
                                    if (!var17[player]) {
                                        this.method154((var46 - 32) / 2, player, var35, var34);
                                        var17[player] = true;
                                    }
                                }
                            }

                            if (var45 == 0) {
                                var17[player] = false;
                            }

                            if (var33 == 28 || var33 == 30) {
                                this.method155(var33 == 30, player, var35, var34, var3, var4);
                            }

                            this.method152(player, var32, var31, var30, var29, var28, var27, var26, var25, var35, var34, var3, var4);
                        }

                        boolean var47 = this.method148(player, var33);
                        boolean var48 = false;
                        if (this.aShortArrayArrayArray2825 != null && !var21 && !var15[player] && !var16[player]) {
                            var48 = this.method149(player, var35, var34);
                        }

                        var20 = false;
                        double var49;
                        if (var33 == 25 || super.isSolidArrayIThink[var35][var34 - 1] == 25 || super.isSolidArrayIThink[var35 + 1][var34] == 25 || super.isSolidArrayIThink[var35][var34 + 1] == 25 || super.isSolidArrayIThink[var35 - 1][var34] == 25) {
                            var49 = var33 == 25 ? 1.0D : 0.5D;
                            var20 = true;
                            int var51 = 0;
                            if (var32 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] += var49 * 0.03D;
                            }

                            if (var31 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] += var49 * 0.03D * aDouble2798;
                                this.ballVelocityX[player] -= var49 * 0.03D * aDouble2798;
                            }

                            if (var30 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityX[player] -= var49 * 0.03D;
                            }

                            if (var29 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] -= var49 * 0.03D * aDouble2798;
                                this.ballVelocityX[player] -= var49 * 0.03D * aDouble2798;
                            }

                            if (var28 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] -= var49 * 0.03D;
                            }

                            if (var27 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] -= var49 * 0.03D * aDouble2798;
                                this.ballVelocityX[player] += var49 * 0.03D * aDouble2798;
                            }

                            if (var26 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityX[player] += var49 * 0.03D;
                            }

                            if (var25 == 25) {
                                ++var51;
                            } else {
                                this.ballVelocityY[player] += var49 * 0.03D * aDouble2798;
                                this.ballVelocityX[player] += var49 * 0.03D * aDouble2798;
                            }

                            if (var51 >= 7) {
                                var20 = false;
                                var15[player] = true;
                                this.ballVelocityX[player] = this.ballVelocityY[player] = 0.0D;
                            }
                        }

                        if (var20) {
                            ++var18[player];
                            if (var18[player] > 500) {
                                var20 = false;
                            }
                        } else {
                            var18[player] = 0;
                        }

                        if (!var47 && !var48 && !var20 && !var15[player] && !var16[player] && !var21) {
                            var11[player] = this.playerX[player];
                            var12[player] = this.playerY[player];
                        }

                        var36 = Math.sqrt(this.ballVelocityX[player] * this.ballVelocityX[player] + this.ballVelocityY[player] * this.ballVelocityY[player]);
                        if (var36 > 0.0D) {
                            double var52 = this.method150(var33, var36);
                            this.ballVelocityX[player] *= var52;
                            this.ballVelocityY[player] *= var52;
                            var36 *= var52;
                            if (var36 > 7.0D) {
                                var49 = 7.0D / var36;
                                this.ballVelocityX[player] *= var49;
                                this.ballVelocityY[player] *= var49;
                                var36 *= var49;
                            }
                        }

                        if (var5 > 4000) {
                            this.aDouble2819 = 0.0D;
                            if (var5 > 7000) {
                                var48 = false;
                                var47 = false;
                                var36 = 0.0D;
                            }
                        }

                        if (var47 && var36 < 0.22499999999999998D) {
                            ++var7[player];
                            if (var7[player] >= 250) {
                                var47 = false;
                            }
                        } else {
                            var7[player] = 0;
                        }

                        if (var48 && var36 < 0.22499999999999998D) {
                            ++var6[player];
                            if (var6[player] >= 150) {
                                var48 = false;
                            }
                        } else {
                            var6[player] = 0;
                        }

                        if (var36 < 0.075D && !var47 && !var48 && !var20 && !var15[player] && !var16[player]) {
                            this.ballVelocityX[player] = this.ballVelocityY[player] = 0.0D;
                            if (var33 != 12 && var33 != 14 && var33 != 13 && var33 != 15) {
                                ++var24;
                            } else {
                                var16[player] = true;
                            }
                        }

                        if (var15[player] || var16[player]) {
                            var10[player] += 0.1D;
                            if (var15[player] && var10[player] > 2.1666666666666665D || var16[player] && var10[player] > 6.0D) {
                                if (var33 == 25) {
                                    this.aSynchronizedBoolArray2831[player].set(true);
                                    if (this.aBoolean2832 && this.playerCount > 1) {
                                        super.gameContainer.gamePanel.hideSkipButton();
                                    }
                                } else {
                                    if (var33 == 12 || var33 == 14) {
                                        this.playerX[player] = this.anInt2810 == 0 ? var8[player] : var11[player];
                                        this.playerY[player] = this.anInt2810 == 0 ? var9[player] : var12[player];
                                    }

                                    if (var33 == 13 || var33 == 15) {
                                        this.method145(player, false);
                                    }

                                    var10[player] = 0.0D;
                                }

                                var15[player] = var16[player] = false;
                                ++var24;
                            }
                        }
                    } else {
                        ++var24;
                    }
                }

                ++var5;
                if (var24 >= this.playerCount) {
                    var42 = this.anInt2839;
                }
            }

            for (player = 0; player < this.playerCount; ++player) {
                if (this.aBooleanArray2830[player]) {
                    int var54 = (int) (var13[player] - 6.5D + 0.5D);
                    int var55 = (int) (var14[player] - 6.5D + 0.5D);
                    int var56 = var54 + 13;
                    int var57 = var55 + 13;
                    var3.drawImage(var2, var54, var55, var56, var57, var54, var55, var56, var57, this);

                    for (var44 = 0; var44 < this.playerCount; ++var44) {
                        if (this.aBooleanArray2830[var44] && var44 != this.currentPlayerID) {
                            this.drawBall(var3, var44, var10[var44]);
                        }
                    }

                    this.drawBall(var3, this.currentPlayerID, var10[this.currentPlayerID]);
                    if (this.playerX[player] < var13[player]) {
                        var54 = (int) (this.playerX[player] - 6.5D + 0.5D);
                    }

                    if (this.playerX[player] > var13[player]) {
                        var56 = (int) (this.playerX[player] - 6.5D + 0.5D) + 13;
                    }

                    if (this.playerY[player] < var14[player]) {
                        var55 = (int) (this.playerY[player] - 6.5D + 0.5D);
                    }

                    if (this.playerY[player] > var14[player]) {
                        var57 = (int) (this.playerY[player] - 6.5D + 0.5D) + 13;
                    }

                    var4.drawImage(var1, var54, var55, var56, var57, var54, var55, var56, var57, this);
                }
            }

            var39 = System.currentTimeMillis() - var39;
            long var58 = (long) (6 * this.anInt2839) - var39;
            if (var23) {
                if (var22) {
                    var58 = 0L;
                } else if (var5 % 100 == 0) {
                    var22 = super.gameContainer.gamePanel.maxFps();
                }
            }

            Tools.sleep(var58);
            var38 = (int) ((long) var38 + var58);
        } while (var24 < this.playerCount && !this.aBoolean2843);

        if (this.aBoolean2843) {
            this.aThread2842 = null;
        } else {
            this.method164(var38);
            super.gameContainer.gamePanel.sendEndStroke(this.currentPlayerID, this.aSynchronizedBoolArray2831, this.anInt2816);
            if (this.anInt2816 >= 0) {
                this.aSynchronizedBoolArray2831[this.anInt2816].set(true);
            }

            this.aThread2842 = null;
            this.repaint();
        }
    }

    public void mouseMoved(MouseEvent var1) {
        this.mouseX = var1.getX();
        this.mouseY = var1.getY();
        this.repaint();
    }

    public void mouseDragged(MouseEvent var1) {
    }

    public void mouseEntered(MouseEvent var1) {
        this.mouseMoved(var1);
    }

    public void mouseExited(MouseEvent var1) {
        this.mouseX = this.mouseY = -1;
        this.repaint();
    }

    public synchronized void mousePressed(MouseEvent event) {
        if (this.gameState == 1) {
            if (event.isMetaDown()) {
                this.mouseInputMode = (this.mouseInputMode + 1) % 4;
                this.repaint();
            } else {
                int x = event.getX();
                int y = event.getY();
                this.mouseX = x;
                this.mouseY = y;
                double subtractionX = this.playerX[this.currentPlayerID] - (double) x;
                double subtractionY = this.playerY[this.currentPlayerID] - (double) y;
                if (Math.sqrt(subtractionX * subtractionX + subtractionY * subtractionY) >= 6.5D) {
                    this.removeMouseMotionListener(this);
                    this.removeMouseListener(this);
                    this.removeKeyListener(this);
                    this.setCursor(cursorDefault);
                    if (super.gameContainer.gamePanel.canStroke(true)) {
                        super.gameContainer.gamePanel.setBeginStroke(this.currentPlayerID, x, y, this.mouseInputMode);
                        this.doStroke(this.currentPlayerID, true, x, y, this.mouseInputMode);
                    }
                }

            }
        }
    }

    public void mouseReleased(MouseEvent var1) {
        if (this.gameState == 1) {
            var1.consume();
        }

    }

    public void mouseClicked(MouseEvent var1) {
    }

    public synchronized void keyPressed(KeyEvent var1) {
        if (this.gameState == 1) {
            this.mouseInputMode = (this.mouseInputMode + 1) % 4;
            this.repaint();
        }
    }

    public void keyReleased(KeyEvent var1) {
    }

    public void keyTyped(KeyEvent var1) {
    }

    protected void initVariables(int playerCount, int var2, int var3) {
        this.playerCount = playerCount;
        this.anInt2810 = var2;
        this.anInt2811 = var3;
        this.playerX = new double[playerCount];
        this.playerY = new double[playerCount];
        this.ballVelocityX = new double[playerCount];
        this.ballVelocityY = new double[playerCount];
        this.aSynchronizedBoolArray2831 = new SynchronizedBool[playerCount];

        for (int var4 = 0; var4 < playerCount; ++var4) {
            this.aSynchronizedBoolArray2831[var4] = new SynchronizedBool();
        }

        this.aBooleanArray2830 = new boolean[playerCount];
        this.aBooleanArray2834 = new boolean[playerCount];
        this.showPlayerNameState = playerCount <= 2 ? 0 : 3;
    }

    protected void drawMap(int var1) {
        super.drawMap(var1);
        this.currentPlayerID = this.mouseX = this.mouseY = -1;
        this.gameState = 0;
        this.repaint();
    }

    protected boolean parseMap(String commandLines, String var2, int gameId) {
        boolean var4 = super.parseMapCommands(commandLines);

        this.aString2835 = null;
        StringTokenizer commnadTokens = new StringTokenizer(commandLines, "\n");

        int var10;
        while (commnadTokens.hasMoreTokens()) {
            String currentCommand = commnadTokens.nextToken();
            char commandType = currentCommand.charAt(0);

            if (commandType == 'B' || commandType == 'L') {

                int var8 = currentCommand.indexOf(',');
                int var9 = currentCommand.indexOf(',', var8 + 1);
                var10 = currentCommand.indexOf(',', var9 + 1);
                currentCommand = currentCommand.substring(var10 + 1);
                int var11 = currentCommand.indexOf('=');
                if (var11 > -1) {
                    gameId = Integer.parseInt(currentCommand.substring(0, var11));
                    this.aString2835 = currentCommand.substring(var11 + 1);

                }
            }
        }

        Vector var40 = new Vector();
        this.multiballStartX = new double[4];
        this.multiBallStartY = new double[4];
        this.aVectorArray2824 = new Vector[4];
        this.aVectorArray2823 = new Vector[4];
        Vector var41 = new Vector();

        for (var10 = 0; var10 < 4; ++var10) {
            this.multiballStartX[var10] = this.multiBallStartY[var10] = -1.0D;
            this.aVectorArray2824[var10] = new Vector();
            this.aVectorArray2823[var10] = new Vector();
        }

        int x;
        double[] var19;
        // Iterates over the 49*25 map
        // y = rows, x = columns
        for (int y = 0; y < 25; ++y) {
            for (x = 0; x < 49; ++x) {
                // handling for startposition of ball (and maybe multiple start positions?)
                if (super.trackTiles[x][y] / 16777216 == 2) {
                    // special index
                    int var14 = super.trackTiles[x][y] / 65536 % 256 + 24;
                    double centerX = (double) (x * 15) + 7.5D;
                    double centerY = (double) (y * 15) + 7.5D;
                    // default ball
                    if (var14 == 24) {
                        var19 = new double[]{centerX, centerY};
                        var40.addElement(var19);
                    }

                    // blue/red/yellow/green ball
                    if (var14 >= 48 && var14 <= 51) {
                        this.multiballStartX[var14 - 48] = centerX;
                        this.multiBallStartY[var14 - 48] = centerY;
                    }

                    int var20;
                    // teleport input (4 arrow icon)
                    if (var14 == 33 || var14 == 35 || var14 == 37 || var14 == 39) {
                        var20 = (var14 - 33) / 2;
                        var19 = new double[]{centerX, centerY};
                        this.aVectorArray2824[var20].addElement(var19);
                    }

                    // teleport output (filled icon)
                    if (var14 == 32 || var14 == 34 || var14 == 36 || var14 == 38) {
                        var20 = (var14 - 32) / 2;
                        var19 = new double[]{centerX, centerY};
                        this.aVectorArray2823[var20].addElement(var19);
                    }

                    // special arrow field (icon with 4 arrows)
                    if (var14 == 44 || var14 == 45) {
                        int[] var43 = new int[]{(int) (centerX + 0.5D), (int) (centerY + 0.5D), var14};
                        var41.addElement(var43);
                    }
                }
            }
        }

        x = var40.size();
        if (x == 0) {
            this.startPointX = this.startPointY = -1.0D;
        } else {
            var19 = (double[]) var40.elementAt(gameId % x);
            this.startPointX = var19[0];
            this.startPointY = var19[1];
        }

        int var42 = var41.size();
        if (var42 == 0) {
            this.aShortArrayArrayArray2825 = null;
        } else {
            this.aShortArrayArrayArray2825 = new short[147][75][2];

            for (int var21 = 2; var21 < 375; var21 += 5) {
                for (int var22 = 2; var22 < 735; var22 += 5) {
                    double var23 = 0.0D;
                    double var25 = 0.0D;

                    for (int var27 = 0; var27 < var42; ++var27) {
                        int[] var28 = (int[]) var41.elementAt(var27);
                        double var29 = (double) (var28[0] - var22);
                        double var31 = (double) (var28[1] - var21);
                        double var33 = Math.sqrt(var29 * var29 + var31 * var31);
                        if (var33 <= 127.0D) {
                            double var35 = Math.abs(var29) / var33;
                            var33 = 127.0D - var33;
                            var29 = (var29 < 0.0D ? -1.0D : 1.0D) * var33 * var35;
                            var31 = (var31 < 0.0D ? -1.0D : 1.0D) * var33 * (1.0D - var35);
                            if (var28[2] == 45) {
                                var29 = -var29;
                                var31 = -var31;
                            }

                            var25 += var29;
                            var23 += var31;
                        }
                    }

                    int var37 = (int) var25;
                    int var38 = (int) var23;
                    if (var37 < -0x7ff) {
                        var37 = -0x7ff;
                    }

                    if (var37 > 0x7ff) {
                        var37 = 0x7ff;
                    }

                    if (var38 < -0x7ff) {
                        var38 = -0x7ff;
                    }

                    if (var38 > 0x7ff) {
                        var38 = 0x7ff;
                    }

                    this.aShortArrayArrayArray2825[var22 / 5][var21 / 5][0] = (short) var37;
                    this.aShortArrayArrayArray2825[var22 / 5][var21 / 5][1] = (short) var38;
                }
            }
        }

        for (int var39 = 0; var39 < this.playerCount; ++var39) {
            this.aBooleanArray2834[var39] = true;
            this.method145(var39, true);
            this.aSynchronizedBoolArray2831[var39].set(false);
            this.aBooleanArray2830[var39] = var2.charAt(var39) == 't';
        }

        this.aSeed_2836 = new Seed((long) gameId);
        this.repaint();
        return var4;
    }

    protected boolean method134() {
        return this.aString2835 != null;
    }

    protected void method135(int var1, boolean var2, boolean var3) {
        this.currentPlayerID = var1;
        this.aBooleanArray2834[var1] = true;
        this.mouseX = this.mouseY = -1;
        this.mouseInputMode = 0;
        if (var2) {
            this.method162(var3);
            this.gameState = 1;
        } else {
            this.gameState = 0;
        }

        this.repaint();
    }

    protected void decodeCoords(int playerId, boolean isLocalPlayer, String encoded) {
        int var4 = Integer.parseInt(encoded, 36);
        int x = var4 / 1500;
        int y = var4 % 1500 / 4;
        int mod = var4 % 4;
        this.doStroke(playerId, isLocalPlayer, x, y, mod);
    }

    protected boolean method137() {
        return this.gameState == 1;
    }

    protected void endGame() {
        this.removeMouseMotionListener(this);
        this.removeMouseListener(this);
        this.removeKeyListener(this);
        this.setCursor(cursorDefault);
        this.gameState = 0;
        this.repaint();
    }

    protected void method139(int var1) {
        this.showPlayerNameState = var1;
        this.repaint();
    }

    protected boolean getSynchronizedBool(int index) {
        return this.aSynchronizedBoolArray2831[index].get();
    }

    protected void restartGame() {
        this.removeMouseMotionListener(this);
        this.removeMouseListener(this);
        this.removeKeyListener(this);
        this.setCursor(cursorDefault);
        if (this.aThread2842 != null) {
            this.aBoolean2843 = true;

            while (this.aThread2842 != null) {
                Tools.sleep(100L);
            }
        }

        this.gameState = 0;
        this.repaint();
    }

    protected String method142() {
        if (this.gameState != 1) {
            return null;
        } else {
            try {
                String var1 = this.aString2835.substring(0, 4);
                this.aString2835 = this.aString2835.substring(4);
                return var1;
            } catch (StringIndexOutOfBoundsException var2) {
                return null;
            }
        }
    }

    protected void stop() {
        this.removeMouseMotionListener(this);
        this.removeMouseListener(this);
        this.removeKeyListener(this);
        this.setCursor(cursorDefault);
        int x = (int) this.playerX[this.currentPlayerID];
        int y = (int) this.playerY[this.currentPlayerID];
        super.gameContainer.gamePanel.setBeginStroke(this.currentPlayerID, x, y, 0);
        this.doStroke(this.currentPlayerID, true, x, y, 0);
    }

    private void doStroke(int playerId, boolean isLocalPlayer, int mouseX, int mouseY, int mod) {
        this.anInt2816 = super.gameContainer.gamePanel.isValidPlayerID(playerId) ? playerId : -1;
        double[] velocity = this.calculateVelocity(playerId, mouseX, mouseY);
        this.ballVelocityX[playerId] = velocity[0];
        this.ballVelocityY[playerId] = velocity[1];
        System.out.println("start velocityX: " + this.ballVelocityX[playerId]);
        System.out.println("start velocityY: " + this.ballVelocityY[playerId]);
        if (mod == 1) {
            this.ballVelocityX[playerId] = -this.ballVelocityX[playerId];
            this.ballVelocityY[playerId] = -this.ballVelocityY[playerId];
        }

        double var7;
        if (mod == 2) {
            var7 = this.ballVelocityX[playerId];
            this.ballVelocityX[playerId] = this.ballVelocityY[playerId];
            this.ballVelocityY[playerId] = -var7;
        }

        if (mod == 3) {
            var7 = this.ballVelocityX[playerId];
            this.ballVelocityX[playerId] = -this.ballVelocityY[playerId];
            this.ballVelocityY[playerId] = var7;
        }

        var7 = Math.sqrt(this.ballVelocityX[playerId] * this.ballVelocityX[playerId] + this.ballVelocityY[playerId] * this.ballVelocityY[playerId]);
        double var9 = var7 / 6.5D;
        var9 *= var9;
        this.ballVelocityX[playerId] += var9 * ((double) (this.aSeed_2836.next() % '\uc351') / 100000.0D - 0.25D);
        this.ballVelocityY[playerId] += var9 * ((double) (this.aSeed_2836.next() % '\uc351') / 100000.0D - 0.25D);
        this.aBoolean2832 = isLocalPlayer;
        this.gameState = 2;
        this.aBoolean2843 = false;

        System.out.println("aftercalc velocityX: " + this.ballVelocityX[playerId]);
        System.out.println("aftercalc velocityY: " + this.ballVelocityY[playerId]);
        this.aThread2842 = new Thread(this);
        this.aThread2842.start();
    }


    private void method145(int var1, boolean var2) {
        if (this.multiballStartX[var1] >= 0.0D && this.multiballStartX[var1] >= 0.0D) {
            this.playerX[var1] = this.multiballStartX[var1];
            this.playerY[var1] = this.multiBallStartY[var1];
        } else if (this.startPointX >= 0.0D && this.startPointY >= 0.0D) {
            this.playerX[var1] = this.startPointX;
            this.playerY[var1] = this.startPointY;
            if (var2) {
                this.aBooleanArray2834[var1] = false;
            }

        } else {
            this.playerX[var1] = 367.5D;
            this.playerY[var1] = 187.5D;
        }
    }

    // calculate stroke ending for mouseX and mouseY
    private double[] calculateVelocity(int playerId, int mouseX, int mouseY) {
        double subX = this.playerX[playerId] - (double) mouseX;
        double subY = this.playerY[playerId] - (double) mouseY;
        double sqrtXY = Math.sqrt(subX * subX + subY * subY);
        double var10 = (sqrtXY - 5.0D) / 30.0D;
        if (var10 < 0.075D) {
            var10 = 0.075D;
        }

        if (var10 > 6.5D) {
            var10 = 6.5D;
        }

        double var12 = var10 / sqrtXY;
        double[] var14 = new double[]{((double) mouseX - this.playerX[playerId]) * var12, ((double) mouseY - this.playerY[playerId]) * var12};
        return var14;
    }

    private boolean method147(int var1, int var2) {
        double var3 = this.playerX[var2] - this.playerX[var1];
        double var5 = this.playerY[var2] - this.playerY[var1];
        double var7 = Math.sqrt(var3 * var3 + var5 * var5);
        if (var7 != 0.0D && var7 <= 13.0D) {
            double var9 = var3 / var7;
            double var11 = var5 / var7;
            double var13 = this.ballVelocityX[var1] * var9 + this.ballVelocityY[var1] * var11;
            double var15 = this.ballVelocityX[var2] * var9 + this.ballVelocityY[var2] * var11;
            if (var13 - var15 <= 0.0D) {
                return false;
            } else {
                double var17 = -this.ballVelocityX[var1] * var11 + this.ballVelocityY[var1] * var9;
                double var19 = -this.ballVelocityX[var2] * var11 + this.ballVelocityY[var2] * var9;
                this.ballVelocityX[var1] = var15 * var9 - var17 * var11;
                this.ballVelocityY[var1] = var15 * var11 + var17 * var9;
                this.ballVelocityX[var2] = var13 * var9 - var19 * var11;
                this.ballVelocityY[var2] = var13 * var11 + var19 * var9;
                return true;
            }
        } else {
            return false;
        }
    }

    private boolean method148(int var1, int var2) {
        if (var2 >= 4 && var2 <= 11) {
            if (var2 == 4) {
                this.ballVelocityY[var1] -= 0.025D;
            }

            if (var2 == 5) {
                this.ballVelocityY[var1] -= 0.025D * aDouble2798;
                this.ballVelocityX[var1] += 0.025D * aDouble2798;
            }

            if (var2 == 6) {
                this.ballVelocityX[var1] += 0.025D;
            }

            if (var2 == 7) {
                this.ballVelocityY[var1] += 0.025D * aDouble2798;
                this.ballVelocityX[var1] += 0.025D * aDouble2798;
            }

            if (var2 == 8) {
                this.ballVelocityY[var1] += 0.025D;
            }

            if (var2 == 9) {
                this.ballVelocityY[var1] += 0.025D * aDouble2798;
                this.ballVelocityX[var1] -= 0.025D * aDouble2798;
            }

            if (var2 == 10) {
                this.ballVelocityX[var1] -= 0.025D;
            }

            if (var2 == 11) {
                this.ballVelocityY[var1] -= 0.025D * aDouble2798;
                this.ballVelocityX[var1] -= 0.025D * aDouble2798;
            }

            return true;
        } else {
            return false;
        }
    }

    private boolean method149(int var1, int var2, int var3) {
        int var4 = var2 / 5;
        int var5 = var3 / 5;
        short var6 = this.aShortArrayArrayArray2825[var4][var5][0];
        short var7 = this.aShortArrayArrayArray2825[var4][var5][1];
        if (var6 == 0 && var7 == 0) {
            return false;
        } else {
            if (this.aDouble2820 > 0.0D) {
                this.aDouble2820 -= 1.0E-4D;
            }

            this.ballVelocityX[var1] += this.aDouble2820 * (double) var6 * 5.0E-4D;
            this.ballVelocityY[var1] += this.aDouble2820 * (double) var7 * 5.0E-4D;
            return true;
        }
    }

    private double method150(int var1, double var2) {
        double var4 = this.method151(var1);
        double var6 = 0.75D * var2 / 6.5D;
        double var8 = 1.0D - var4;
        return var4 + var8 * var6;
    }

    private double method151(int var1) {
        return var1 != 0 && (var1 < 4 || var1 > 11) && var1 != 19 && var1 != 47 ? (var1 == 1 ? 0.92D : (var1 == 2 ? 0.8D : (var1 != 3 && var1 != 32 && var1 != 34 && var1 != 36 && var1 != 38 ? (var1 != 12 && var1 != 13 ? (var1 != 14 && var1 != 15 ? (var1 >= 20 && var1 <= 23 ? 0.995D : (var1 == 25 ? 0.96D : (var1 != 28 && var1 != 30 ? (var1 != 29 && var1 != 31 ? (var1 == 44 ? 0.9D : 1.0D) : 0.9D) : 1.0D))) : 0.95D) : 0.0D) : 0.9975D))) : 0.9935D;
    }

    private void method152(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, Graphics var12, Graphics var13) {
        boolean var14 = var2 >= 16 && var2 <= 23 && var2 != 19 || var2 == 27 || var2 >= 40 && var2 <= 43 || var2 == 46;
        boolean var15 = var3 >= 16 && var3 <= 23 && var3 != 19 || var3 == 27 || var3 >= 40 && var3 <= 43 || var3 == 46;
        boolean var16 = var4 >= 16 && var4 <= 23 && var4 != 19 || var4 == 27 || var4 >= 40 && var4 <= 43 || var4 == 46;
        boolean var17 = var5 >= 16 && var5 <= 23 && var5 != 19 || var5 == 27 || var5 >= 40 && var5 <= 43 || var5 == 46;
        boolean var18 = var6 >= 16 && var6 <= 23 && var6 != 19 || var6 == 27 || var6 >= 40 && var6 <= 43 || var6 == 46;
        boolean var19 = var7 >= 16 && var7 <= 23 && var7 != 19 || var7 == 27 || var7 >= 40 && var7 <= 43 || var7 == 46;
        boolean var20 = var8 >= 16 && var8 <= 23 && var8 != 19 || var8 == 27 || var8 >= 40 && var8 <= 43 || var8 == 46;
        boolean var21 = var9 >= 16 && var9 <= 23 && var9 != 19 || var9 == 27 || var9 >= 40 && var9 <= 43 || var9 == 46;
        if (var14 && var2 == 20) {
            var14 = false;
        }

        if (var21 && var9 == 20) {
            var21 = false;
        }

        if (var15 && var3 == 20) {
            var15 = false;
        }

        if (var20 && var8 == 20) {
            var20 = false;
        }

        if (var16 && var4 == 20) {
            var16 = false;
        }

        if (var16 && var4 == 21) {
            var16 = false;
        }

        if (var15 && var3 == 21) {
            var15 = false;
        }

        if (var17 && var5 == 21) {
            var17 = false;
        }

        if (var14 && var2 == 21) {
            var14 = false;
        }

        if (var18 && var6 == 21) {
            var18 = false;
        }

        if (var18 && var6 == 22) {
            var18 = false;
        }

        if (var17 && var5 == 22) {
            var17 = false;
        }

        if (var19 && var7 == 22) {
            var19 = false;
        }

        if (var16 && var4 == 22) {
            var16 = false;
        }

        if (var20 && var8 == 22) {
            var20 = false;
        }

        if (var20 && var8 == 23) {
            var20 = false;
        }

        if (var19 && var7 == 23) {
            var19 = false;
        }

        if (var21 && var9 == 23) {
            var21 = false;
        }

        if (var18 && var6 == 23) {
            var18 = false;
        }

        if (var14 && var2 == 23) {
            var14 = false;
        }

        if (var14 && var15 && var16 && (var2 < 20 || var2 > 23) && (var3 < 20 || var3 > 23) && (var4 < 20 || var4 > 23)) {
            var16 = false;
            var14 = false;
        }

        if (var16 && var17 && var18 && (var4 < 20 || var4 > 23) && (var5 < 20 || var5 > 23) && (var6 < 20 || var6 > 23)) {
            var18 = false;
            var16 = false;
        }

        if (var18 && var19 && var20 && (var6 < 20 || var6 > 23) && (var7 < 20 || var7 > 23) && (var8 < 20 || var8 > 23)) {
            var20 = false;
            var18 = false;
        }

        if (var20 && var21 && var14 && (var8 < 20 || var8 > 23) && (var9 < 20 || var9 > 23) && (var2 < 20 || var2 > 23)) {
            var14 = false;
            var20 = false;
        }

        double var22;
        if (!var14 && !var16 && !var18 && !var20) {
            double var24;
            if (var15 && (this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] < 0.0D || this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] < 0.0D && -this.ballVelocityY[var1] > -this.ballVelocityX[var1] || this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] > 0.0D && this.ballVelocityX[var1] > this.ballVelocityY[var1])) {
                var22 = this.method153(var3, var1, var10 + anInt2799, var11 - anInt2799, var12, var13, 1, -1);
                var24 = this.ballVelocityX[var1];
                this.ballVelocityX[var1] = this.ballVelocityY[var1] * var22;
                this.ballVelocityY[var1] = var24 * var22;
            }

            if (var17 && (this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] > 0.0D || this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] < 0.0D && this.ballVelocityX[var1] > -this.ballVelocityY[var1] || this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] > 0.0D && this.ballVelocityY[var1] > -this.ballVelocityX[var1])) {
                var22 = this.method153(var5, var1, var10 + anInt2799, var11 + anInt2799, var12, var13, 1, 1);
                var24 = this.ballVelocityX[var1];
                this.ballVelocityX[var1] = -this.ballVelocityY[var1] * var22;
                this.ballVelocityY[var1] = -var24 * var22;
            }

            if (var19 && (this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] > 0.0D || this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] > 0.0D && this.ballVelocityY[var1] > this.ballVelocityX[var1] || this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] < 0.0D && -this.ballVelocityX[var1] > -this.ballVelocityY[var1])) {
                var22 = this.method153(var7, var1, var10 - anInt2799, var11 + anInt2799, var12, var13, -1, 1);
                var24 = this.ballVelocityX[var1];
                this.ballVelocityX[var1] = this.ballVelocityY[var1] * var22;
                this.ballVelocityY[var1] = var24 * var22;
            }

            if (var21 && (this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] < 0.0D || this.ballVelocityX[var1] < 0.0D && this.ballVelocityY[var1] > 0.0D && -this.ballVelocityX[var1] > this.ballVelocityY[var1] || this.ballVelocityX[var1] > 0.0D && this.ballVelocityY[var1] < 0.0D && -this.ballVelocityY[var1] > this.ballVelocityX[var1])) {
                var22 = this.method153(var9, var1, var10 - anInt2799, var11 - anInt2799, var12, var13, -1, -1);
                var24 = this.ballVelocityX[var1];
                this.ballVelocityX[var1] = -this.ballVelocityY[var1] * var22;
                this.ballVelocityY[var1] = -var24 * var22;
            }
        } else {
            if (var14 && this.ballVelocityY[var1] < 0.0D) {
                var22 = this.method153(var2, var1, var10, var11 - 6, var12, var13, 0, -1);
                this.ballVelocityX[var1] *= var22;
                this.ballVelocityY[var1] *= -var22;
            } else if (var18 && this.ballVelocityY[var1] > 0.0D) {
                var22 = this.method153(var6, var1, var10, var11 + 6, var12, var13, 0, 1);
                this.ballVelocityX[var1] *= var22;
                this.ballVelocityY[var1] *= -var22;
            }

            if (var16 && this.ballVelocityX[var1] > 0.0D) {
                var22 = this.method153(var4, var1, var10 + 6, var11, var12, var13, 1, 0);
                this.ballVelocityX[var1] *= -var22;
                this.ballVelocityY[var1] *= var22;
                return;
            }

            if (var20 && this.ballVelocityX[var1] < 0.0D) {
                var22 = this.method153(var8, var1, var10 - 6, var11, var12, var13, -1, 0);
                this.ballVelocityX[var1] *= -var22;
                this.ballVelocityY[var1] *= var22;
                return;
            }
        }

    }

    private double method153(int var1, int var2, int var3, int var4, Graphics var5, Graphics var6, int var7, int var8) {
        if (var1 == 16) {
            return 0.81D;
        } else if (var1 == 17) {
            return 0.05D;
        } else if (var1 == 18) {
            if (this.aDouble2819 <= 0.0D) {
                return 0.84D;
            } else {
                this.aDouble2819 -= 0.01D;
                double var9 = Math.sqrt(this.ballVelocityX[var2] * this.ballVelocityX[var2] + this.ballVelocityY[var2] * this.ballVelocityY[var2]);
                return this.aDouble2819 * 6.5D / var9;
            }
        } else if (var1 != 20 && var1 != 21 && var1 != 22 && var1 != 23) {
            if (var1 != 27 && var1 != 46) {
                if (var1 != 40 && var1 != 41 && var1 != 42 && var1 != 43) {
                    return 1.0D;
                } else {
                    this.method159(var3, var4, var5, var6);
                    return 0.9D;
                }
            } else {
                return this.method156(var3, var4, var5, var6, var7, var8, var1 == 27) ? 0.325D : 0.8D;
            }
        } else {
            return 0.82D;
        }
    }

    private void method154(int var1, int var2, int var3, int var4) {
        boolean var5 = true;
        int var6 = this.aVectorArray2824[var1].size();
        int var7;
        int var8;
        double[] var11;
        int var13;
        if (var6 > 0) {
            var13 = var1;
            var7 = var6 - 1;
            var8 = this.aSeed_2836.next() % (var7 + 1);
        } else {
            var7 = this.aVectorArray2823[var1].size();
            int var10;
            if (var7 >= 2) {
                int var14 = 0;

                do {
                    var10 = var7 - 1;
                    var8 = this.aSeed_2836.next() % (var10 + 1);
                    var11 = (double[]) this.aVectorArray2823[var1].elementAt(var8);
                    if (Math.abs(var11[0] - (double) var3) >= 15.0D || Math.abs(var11[1] - (double) var4) >= 15.0D) {
                        this.playerX[var2] = var11[0];
                        this.playerY[var2] = var11[1];
                        return;
                    }

                    ++var14;
                } while (var14 < 100);

                return;
            }

            boolean var9 = false;

            for (var10 = 0; var10 < 4 && !var9; ++var10) {
                if (this.aVectorArray2824[var10].size() > 0) {
                    var9 = true;
                }
            }

            if (!var9) {
                return;
            }

            do {
                var13 = this.aSeed_2836.next() % 4;
                var6 = this.aVectorArray2824[var13].size();
            } while (var6 == 0);

            int var12 = var6 - 1;
            var8 = this.aSeed_2836.next() % (var12 + 1);
        }

        var11 = (double[]) this.aVectorArray2824[var13].elementAt(var8);
        this.playerX[var2] = var11[0];
        this.playerY[var2] = var11[1];
    }

    private void method155(boolean var1, int var2, int var3, int var4, Graphics var5, Graphics var6) {
        int var7 = var3 / 15;
        int var8 = var4 / 15;
        int var9 = super.trackTiles[var7][var8] / 16777216;
        int var10 = super.trackTiles[var7][var8] / 65536 % 256 + 24;
        int var11 = super.trackTiles[var7][var8] / 256 % 256;
        int var12 = super.trackTiles[var7][var8] % 256;
        if (var9 == 2 && (var10 == 28 || var10 == 30)) {
            ++var10;
            super.trackTiles[var7][var8] = var9 * 256 * 256 * 256 + (var10 - 24) * 256 * 256 + var11 * 256 + var12;
            this.drawTile(var7, var8, var5, var6);
            if (var1) {
                int[] var13 = new int[]{17039367, 16779264, 17104905, 16778752, -1, 16779776, 17235973, 16778240, 17170443};
                int var14 = 0;

                for (int var15 = var8 - 1; var15 <= var8 + 1; ++var15) {
                    for (int var16 = var7 - 1; var16 <= var7 + 1; ++var16) {
                        if (var16 >= 0 && var16 < 49 && var15 >= 0 && var15 < 25 && (var15 != var8 || var16 != var7) && super.trackTiles[var16][var15] == 16777216) {
                            super.trackTiles[var16][var15] = var13[var14];
                            this.drawTile(var16, var15, var5, var6);
                        }

                        ++var14;
                    }
                }
            }

            double var17;
            do {
                do {
                    this.ballVelocityX[var2] = (double) (-65 + this.aSeed_2836.next() % 131) / 10.0D;
                    this.ballVelocityY[var2] = (double) (-65 + this.aSeed_2836.next() % 131) / 10.0D;
                    var17 = Math.sqrt(this.ballVelocityX[var2] * this.ballVelocityX[var2] + this.ballVelocityY[var2] * this.ballVelocityY[var2]);
                } while (var17 < 5.2D);
            } while (var17 > 6.5D);

            if (!var1) {
                this.ballVelocityX[var2] *= 0.8D;
                this.ballVelocityY[var2] *= 0.8D;
            }

        }
    }

    private boolean method156(int var1, int var2, Graphics var3, Graphics var4, int var5, int var6, boolean var7) {
        int var8 = var1 / 15;
        int var9 = var2 / 15;
        int var10 = super.trackTiles[var8][var9] / 16777216;
        int var11 = super.trackTiles[var8][var9] / 65536 % 256 + 24;
        int var12 = super.trackTiles[var8][var9] / 256 % 256;
        if (var10 == 2 && (var11 == 27 || var11 == 46)) {
            int var13 = var8 + var5;
            int var14 = var9 + var6;
            int var15 = this.method157(var13, var14);
            if (var15 == -1) {
                return false;
            } else {
                super.trackTiles[var8][var9] = 16777216 + var12 * 256;
                this.drawTile(var8, var9, var3, var4);
                int[] var16 = this.method158(var8, var9, var13, var14, var12, var15, var3, var4, var7, 0);
                if (!var7 && (var16[2] == 12 || var16[2] == 13)) {
                    super.trackTiles[var16[0]][var16[1]] = 35061760 + var16[2] * 256;
                } else {
                    super.trackTiles[var16[0]][var16[1]] = 33554432 + ((var7 ? 27 : 46) - 24) * 256 * 256 + var16[2] * 256;
                }

                this.drawTile(var16[0], var16[1], var3, var4);
                return true;
            }
        } else {
            return false;
        }
    }

    private int method157(int var1, int var2) {
        if (var1 >= 0 && var1 < 49 && var2 >= 0 && var2 < 25) {
            int var3 = super.trackTiles[var1][var2] / 16777216;
            int var4 = super.trackTiles[var1][var2] / 65536 % 256;
            int var5 = super.trackTiles[var1][var2] / 256 % 256;
            if (var3 == 1 && var4 == 0 && var5 <= 15) {
                for (int var6 = 0; var6 < this.playerCount; ++var6) {
                    if (this.playerX[var6] > (double) (var1 * 15) && this.playerX[var6] < (double) (var1 * 15 + 15 - 1) && this.playerY[var6] > (double) (var2 * 15) && this.playerY[var6] < (double) (var2 * 15 + 15 - 1)) {
                        return -1;
                    }
                }

                return var5;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private int[] method158(int var1, int var2, int var3, int var4, int var5, int var6, Graphics var7, Graphics var8, boolean var9, int var10) {
        int[] var11 = new int[]{var3, var4, var6};
        if (!var9 && var6 >= 4 && var6 <= 11 && var10 < 1078) {
            var1 = var3;
            var2 = var4;
            var5 = var6;
            if (var6 == 4 || var6 == 5 || var6 == 11) {
                --var4;
            }

            if (var6 == 8 || var6 == 7 || var6 == 9) {
                ++var4;
            }

            if (var6 == 5 || var6 == 6 || var6 == 7) {
                ++var3;
            }

            if (var6 == 9 || var6 == 10 || var6 == 11) {
                --var3;
            }

            var6 = this.method157(var3, var4);
            if (var6 >= 0) {
                var11 = this.method158(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10 + 1);
            }
        }

        return var11;
    }

    private void method159(int var1, int var2, Graphics var3, Graphics var4) {
        int var5 = var1 / 15;
        int var6 = var2 / 15;
        int var7 = super.trackTiles[var5][var6] / 16777216;
        int var8 = super.trackTiles[var5][var6] / 65536 % 256 + 24;
        int var9 = super.trackTiles[var5][var6] / 256 % 256;
        int var10 = super.trackTiles[var5][var6] % 256;
        if (var7 == 2 && var8 >= 40 && var8 <= 43) {
            ++var8;
            if (var8 <= 43) {
                super.trackTiles[var5][var6] = var7 * 256 * 256 * 256 + (var8 - 24) * 256 * 256 + var9 * 256 + var10;
            } else {
                super.trackTiles[var5][var6] = 16777216 + var9 * 256 + var9;
            }

            this.drawTile(var5, var6, var3, var4);
        }
    }

    private void drawTile(int tileX, int tileY, Graphics var3, Graphics var4) {
        Image tile = super.getTileAt(tileX, tileY);
        super.checkSolidTile(tileX, tileY);
        var3.drawImage(tile, tileX * 15, tileY * 15, this);
        var4.drawImage(tile, tileX * 15, tileY * 15, this);
    }

    private void drawBall(Graphics g, int playerid, double var3) {
        // -6,5 and 13pixel smells like ball
        int var5 = (int) (this.playerX[playerid] - 6.5D + 0.5D);
        int var6 = (int) (this.playerY[playerid] - 6.5D + 0.5D);
        int var7 = 13;
        if (var3 > 0.0D) {
            var5 = (int) ((double) var5 + var3);
            var6 = (int) ((double) var6 + var3);
            var7 = (int) ((double) var7 - var3 * 2.0D);
        }

        int var8 = 0;
        if (super.gameContainer.graphicsQualityIndex == 3) {
            var8 = (var5 / 5 + var6 / 5) % 2 * 4;
        }

        if (var3 == 0.0D) {
            g.drawImage(this.ballSprites[playerid + var8], var5, var6, this);
            if (this.showPlayerNameState > 0 && this.aBooleanArray2834[playerid] && this.gameState != 2 && this.playerCount > 1) {
                String[] var9 = super.gameContainer.gamePanel.getPlayerInfo(playerid);
                if (this.showPlayerNameState == 1) {
                    StringDraw.drawString(g, var9[0].substring(0, 1), var5 + 6, var6 + 13 - 3, 0);
                    return;
                }

                int var10 = StringDraw.getStringWidth(g, var9[0]);
                int var11 = var5 + 13 + 2;
                if (this.showPlayerNameState != 2 && var9[1] != null) {
                    String var12 = "[" + var9[1] + "]";
                    int var13 = StringDraw.getStringWidth(g, var12);
                    byte var14 = -1;
                    if (var11 + var10 >= 733 || var11 + var13 >= 733) {
                        var11 = var5 - 2;
                        var14 = 1;
                    }

                    StringDraw.drawOutlinedString(g, backgroundColour, var9[0], var11, var6 + 13 - 3 - 6, var14);
                    StringDraw.drawOutlinedString(g, backgroundColour, var12, var11, var6 + 13 - 3 + 7, var14);
                    return;
                }

                if (var11 + var10 >= 733) {
                    var11 = var5 - 2 - var10;
                }

                StringDraw.drawOutlinedString(g, backgroundColour, var9[0], var11, var6 + 13 - 3, -1);
                return;
            }
        } else {
            g.drawImage(this.ballSprites[playerid + var8], var5, var6, var5 + var7, var6 + var7, 0, 0, 13, 13, this);
        }

    }

    private void method162(boolean var1) {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setCursor(cursorCrosshair);
        this.addKeyListener(this);
        if (var1) {
            //this.requestFocus();//todo this is annoying as fuck
        }

    }

    private void method163(Graphics var1, int var2, int var3, int var4, int var5) {
        int var6 = var4 >= 0 ? var4 : -var4;
        int var7 = var5 >= 0 ? var5 : -var5;
        int var8 = (var6 > var7 ? var6 : var7) / 10;
        double var9 = (double) var2;
        double var11 = (double) var3;
        double var13 = (double) var4 / ((double) var8 * 2.0D);
        double var15 = (double) var5 / ((double) var8 * 2.0D);
        var9 += var13;
        var11 += var15;

        for (int var17 = 0; var17 < var8; ++var17) {
            var1.drawLine((int) var9, (int) var11, (int) (var9 + var13), (int) (var11 + var15));
            var9 += var13 * 2.0D;
            var11 += var15 * 2.0D;
        }

    }

    private void method164(int var1) {
        anIntArray2837[0] = anIntArray2837[1];
        anIntArray2837[1] = anIntArray2837[2];
        if (anIntArray2837[1] < var1) {
            var1 = anIntArray2837[1];
        }

        if (anIntArray2837[0] < var1) {
            var1 = anIntArray2837[0];
        }

        while (var1 > 700 && this.anInt2839 > 1) {
            var1 -= 700;
            --this.anInt2839;
        }

        while (var1 < -2000 && this.anInt2839 < 6) {
            var1 += 2000;
            ++this.anInt2839;
        }

        anIntArray2837[2] = var1;
        anInt2838 = this.anInt2839;
    }

}
