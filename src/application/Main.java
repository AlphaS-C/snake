package application;
	
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Font;
import javafx.scene.canvas.Canvas;


public class Main extends Application {
	static int altura=16;
    static int ancho=16;
    static int tamanioPixeles = 25;
    static boolean GameOver = false;
    static int Paused = -1;
    static int despX = 0;
    static int despY = 0;
    static int cabezaX=0;
    static int cabezaY=0;
    static int ptje=0;
    static int highScore = 0;
    static Matriz juego;
      
	@Override
	public void start(Stage primaryStage) {
		inicializar();
		try {
			VBox root  =new VBox();
			Canvas lienzo = new Canvas(ancho*tamanioPixeles, altura*tamanioPixeles);
			GraphicsContext gc = lienzo.getGraphicsContext2D();
			root.getChildren().add(lienzo);
			
			
			new AnimationTimer() {
				long ultimoTiempo = 0; 
				
				public void handle(long ahora)
				{
					if(ultimoTiempo==0)
					{
						ultimoTiempo = ahora;
						tick(gc);
						return;
					}
					
					if(ahora-ultimoTiempo > 100000000)
					{
						ultimoTiempo = ahora;
						tick(gc);
					}
				}
			}
			.start();
			
			
			Scene scene = new Scene(root, ancho*tamanioPixeles, altura*tamanioPixeles);
			
			
			scene.setOnKeyPressed(e -> {
				
			    if (e.getCode() == KeyCode.DOWN) {
			    	if(despY == -tamanioPixeles) {return;}
			        despY=tamanioPixeles;
			        despX=0;
			    }
			    else if (e.getCode() == KeyCode.UP) {
			    	if(despY == tamanioPixeles) {return;}
			        despY=-tamanioPixeles;
			        despX=0;

			    }
			    else if (e.getCode() == KeyCode.RIGHT) {
			    	if(despX == -tamanioPixeles) {return;}
			        despX= tamanioPixeles;
			        despY=0;
			    }
			    else if (e.getCode() == KeyCode.LEFT) {
			    	if(despX == tamanioPixeles) {return;}
			        despX=-tamanioPixeles;
			        despY=0;
			    }
			    
			    else if (e.getCode() == KeyCode.SPACE) {
			    	
			    	if(GameOver)
			    	{
			    		inicializar();
			    		
			    	}
			    	else
			    	{
			    		Paused*=-1;
			    	}
			    }
			});

			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("SNAKE GAME");
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	public void inicializar()
	{
		juego = new Matriz();
		juego.generarManzana();
	    tamanioPixeles = 25;
	    GameOver = false;
	    despX = 0;
	    despY = 0;
	    cabezaX=0;
	    cabezaY=0;
	    ptje=0;
	}
	
	public static void tick(GraphicsContext gc)
	{
		if(GameOver)
		{
			
			gc.setFill(Color.RED);
			gc.setFont(new Font("impact", 50));
			gc.fillText("Game Over", 100, 200);
			if(ptje>highScore)
			{
				highScore = ptje;
			}
			
			gc.setFill(Color.WHITE);
			gc.setFont(new Font("impact", 25));
			gc.fillText("High Score: "+highScore, 140, 250);
			gc.setFill(Color.GREY);
			gc.setFont(new Font("impact", 15));
			gc.fillText("Press SPACEBAR to restart" , 130, 275);
			
			return;
		}
		
		if(Paused==1)
		{
			gc.setFill(Color.GREY);
			gc.setFont(new Font("impact", 50));
			gc.fillText("Paused", 120, 200);
			return;
		}
		
		
		juego.sumarMatriz(-1);
		juego.colocarNuevaCabeza();
		
		
		if(!GameOver) {
		gc.setFill(Color.BLACK);
		gc.fillRect(0,0,ancho*tamanioPixeles, altura*tamanioPixeles);
		}
		else
		{
		return;
		}
		
		for(int i=0; i<juego.getMatriz().length; i++) {
			for(int j=0; j<juego.getMatriz()[0].length; j++)
			{
				if(juego.getMatriz()[i][j] > 0)
				{
					gc.setFill(Color.GREEN);
					gc.fillRect(i*tamanioPixeles, j*tamanioPixeles, tamanioPixeles, tamanioPixeles);
				}
				else if(juego.getMatriz()[i][j] == -1)
				{
					gc.setFill(Color.RED);
					gc.fillRect(i*tamanioPixeles, j*tamanioPixeles,tamanioPixeles, tamanioPixeles);
				}
			}
		}		
		

		gc.setFill(Color.WHITE);
		gc.setFont(new Font("impact", 30));
		gc.fillText("Score: "+ptje, 10, 30);
	}
	
	public static void main(String[] args) {
		launch(args);
	
	}
}
