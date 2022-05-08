package application;

public class Matriz {

		private int cabezaX;
		private int cabezaY;
		private int valorCabeza = 1;
		private int dirX;
		private int dirY;
		private boolean GameOver = false;
		private int matriz[][];

		public Matriz() {
			
			this.matriz = new int[16][16];
			this.cabezaX = 12;
			this.cabezaY = 3;
		    matriz[cabezaY][cabezaX] = valorCabeza;
		}

	
		
		public void generarManzana() {
			
			int aleatoreo = ((int) (Math.random() * 10000.0)) % 256;
	        int i = (int) aleatoreo / matriz.length;
	        int j = (int) (aleatoreo % matriz.length);
	        if (matriz[i][j] == 0) {
	            matriz[i][j] = -1;
	        }
	        else {
	        	generarManzana();
	        }
		}


		public void sumarMatriz(int valor) {
		for (int i = 0; i < (matriz.length); i++) {
			for (int j= 0; j < (matriz[0].length); j++) {
				if (matriz[i][j] > 0) {
					matriz[i][j] = matriz[i][j] + valor;

					}	
				}
			}

		}

		
	   public void colocarNuevaCabeza() {

		   cabezaX += Main.despX/Main.tamanioPixeles;
		   cabezaY += Main.despY/Main.tamanioPixeles;
		   
		   
		    if (cabezaX > matriz.length-1 || cabezaX < 0 || cabezaY > matriz.length-1 || cabezaY < 0) {
		    	Main.GameOver = true;
		    }
		    
		    else if (matriz[cabezaX][cabezaY] > 0) {
		    	Main.GameOver = true;
		    }

		    
		    else if (matriz[cabezaX][cabezaY] == -1) {
			    matriz[cabezaX][cabezaY] = valorCabeza;
		    	comerManzana();
		    }
		    
		    else {
		    	matriz[cabezaX][cabezaY] = valorCabeza;
		    }
		   
	   }
		
	   public void comerManzana() {
		   sumarMatriz(1);
		   valorCabeza++;
		   generarManzana();
		   Main.ptje++;
	   }
	   
	    public int[][] getMatriz() {
	    	return matriz;
	    }
	    
	    @Override   
	    public String toString() {
	        String resultado = "";
	        for (int i = 0; i < matriz.length; i++) {
	            String linea = "";
	            for (int j = 0; j < matriz[i].length; j++) {
	                linea += matriz[i][j];
	            }
	            resultado += linea + "\n";
	        }
	        return resultado;
	  }
}
