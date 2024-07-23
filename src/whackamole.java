import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class whackamole {
    int boardWidth=750;
    int boardHeight=750;
    JFrame frame=new JFrame("Whack A Mole Mario Edition");
    JLabel textLabel=new JLabel();
    JPanel textPanel=new JPanel();
    JPanel boardPanel=new JPanel();
    
    JButton[] board=new JButton[16];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random=new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;    
    int score=0;

    whackamole(){
        //frame.setVisible(true); causes components to load one by one (slower)
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        textLabel.setFont(new Font("Arial",Font.ITALIC,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("SCORE: 0");
        textLabel.setOpaque(true);
        
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.SOUTH);

        boardPanel.setLayout(new GridLayout(4,4));
        //boardPanel.setBackground(Color.CYAN);
        frame.add(boardPanel);

        //plantIcon=new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImg=new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon=new ImageIcon(plantImg.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));
        Image moleImg=new ImageIcon(getClass().getResource("./monty.png")).getImage();
        moleIcon=new ImageIcon(moleImg.getScaledInstance(150,150,java.awt.Image.SCALE_SMOOTH));

        for(int i=0;i<16;i++){
            JButton tile=new JButton();
            board[i]=tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton tile=(JButton)e.getSource();
                    if(tile==currMoleTile){
                        score+=5;
                        textLabel.setText("SCORE: "+Integer.toString(score));
                    }
                    else if(tile==currPlantTile){
                    textLabel.setText("GAME OVER: "+Integer.toString(score));
                    setMoleTimer.stop();
                    setPlantTimer.stop();
                    for(int i=0;i<16;i++){
                        board[i].setEnabled(false);
                    }
                    }
                }
            });
        }
        //time in milliseconds
        setMoleTimer=new Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(currMoleTile!=null){
                    currMoleTile.setIcon(null);
                    currMoleTile=null;
                }
                //random selection of tiles for mole
                int num=random.nextInt(16);
                JButton tile=board[num];
                //skip tile if plant
                if(currPlantTile==tile) return;
                currMoleTile=tile;
                currMoleTile.setIcon(moleIcon);
            }
        });

        setPlantTimer=new Timer(1500,new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(currPlantTile!=null){
                    currPlantTile.setIcon(null);
                    currPlantTile=null;
                }
                //random selection of tiles for piranha
                int num=random.nextInt(16);
                JButton tile=board[num];
                //skip tile if mole
                if(currMoleTile==tile) return;
                currPlantTile=tile;
                currPlantTile.setIcon(plantIcon);
            }
        });
        setMoleTimer.start();
        setPlantTimer.start();
        //Components loaded first(faster)
        frame.setVisible(true);
    }
}
