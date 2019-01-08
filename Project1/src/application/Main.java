//Omar Barghouthi
//Dr. Majdi Mafarji
//1161728
package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Main extends Application {
	int size;
	int[] power;
	int[] LED;
	int[][] table;
	char[][] map;
	StringBuilder s= new StringBuilder();

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Button FromUser = new Button(" From User ");
			Button FromFile = new Button(" From File ");
			HBox hb1 = new HBox();
			hb1.getChildren().addAll(FromUser, FromFile);
			hb1.setPadding(new Insets(15, 12, 15, 12));
			hb1.setSpacing(10);
			hb1.setStyle("-fx-background-color: #00ffff;");
			FromUser.setPrefSize(100, 20);
			FromFile.setPrefSize(100, 20);
			root.setTop(hb1);

			Text t1 = new Text(" Enter the number of power source you want:-");
			TextField tx1 = new TextField("100");
			GridPane grid = new GridPane();
			HBox hb2 = new HBox();
			hb2.getChildren().addAll(t1, tx1);
			grid.add(hb2, 0, 0);
			root.setCenter(grid);
			hb2.setVisible(false);

			Button Generate = new Button(" Generate ");
			Button LCS = new Button(" LCS ");
			LCS.setDisable(true);
			Button PrintLCS = new Button(" Print LCS ");
			PrintLCS.setDisable(true);
			HBox hb3 = new HBox();
			hb3.getChildren().addAll(Generate, LCS, PrintLCS);
			hb3.setPadding(new Insets(15, 12, 15, 12));
			hb3.setSpacing(10);
			hb3.setStyle("-fx-background-color: #ff1919;");
			Generate.setPrefSize(100, 20);
			LCS.setPrefSize(100, 20);
			PrintLCS.setPrefSize(100, 20);
			hb3.setVisible(false);

			Button LCSf = new Button(" LCS file ");
			LCSf.setDisable(true);
			Button PrintLCSf = new Button(" Print LCS file ");
			PrintLCSf.setDisable(true);
			Button Read = new Button(" Read ");
			HBox hb4 = new HBox();
			hb4.getChildren().addAll(Read, LCSf, PrintLCSf);
			hb4.setPadding(new Insets(15, 12, 15, 12));
			hb4.setSpacing(10);
			hb4.setStyle("-fx-background-color: #ffff00;");
			LCSf.setPrefSize(100, 20);
			PrintLCSf.setPrefSize(100, 20);
			Read.setPrefSize(100, 20);
			hb4.setVisible(false);

			StackPane stack = new StackPane();
			root.setBottom(stack);
			stack.getChildren().addAll(hb3, hb4);
			TextArea txA = new TextArea();
			grid.add(txA, 0, 0);
			txA.setVisible(false);

			FromUser.setOnAction(e -> {
				hb2.setVisible(true);
				hb3.setVisible(true);
				hb4.setVisible(false);
			});
			FromFile.setOnAction(e -> {
				hb2.setVisible(false);
				hb3.setVisible(false);
				hb4.setVisible(true);
			});
			Generate.setOnAction(e -> {
				int n = Integer.parseInt(tx1.getText());
				size = n;
				power = new int[n];
				for (int i = 0; i < n; i++) {
					power[i] = i + 1;
				}
				LED = new int[n];
				int x;
				for (int i = 0; i < n; i++) {
					x = (int) (Math.random() * n + 1);
					while (checkduplicate(LED, x) == false) {
						x = (int) (Math.random() * n + 1);
						checkduplicate(LED, x);
					}
					LED[i] = x;
				}
				LCS.setDisable(false);
				PrintLCS.setDisable(false);
				for (int i = 0; i < n; i++) {
					System.out.println(LED[i]);
				}
			});
			LCS.setOnAction(e -> {
				int[][] table = new int[size + 1][size + 1];
				map = new char[size + 1][size + 1];
				for (int i = 1; i < table.length; i++) {
					for (int j = 1; j < table[i].length; j++) {
						if (LED[i - 1] == power[j - 1]) {
							table[i][j] = table[i - 1][j - 1] + 1;
							map[i][j] = 'D';
						} else if (table[i - 1][j] >= table[i][j - 1]) {
							table[i][j] = table[i - 1][j];
							map[i][j] = 'U';
						} else {
							table[i][j] = table[i][j - 1];
							map[i][j] = 'L';
						}
					}
				}
				txA.setVisible(true);

				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						txA.appendText(table[i][j] + "");
					}
					txA.appendText(" \n ");
				}

			});
			PrintLCS.setOnAction(e -> {
				StringBuilder x = new StringBuilder();
				x=Print(map, power, LED.length, power.length);
				txA.appendText(x.toString());

			});
			Read.setOnAction(e -> {
				File file = new File("Data.txt");
				try {
					Scanner n = new Scanner(file);
					size = n.nextInt();
					power = new int[size];
					LED = new int[size];
					table = new int[size + 1][size + 1];
					for (int i = 0; i < power.length; i++) {
						power[i] = i + 1;
						LED[i] = n.nextInt();
					}
					for (int i = 0; i < LED.length; i++) {
						System.out.println(LED[i]);
					}
					n.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				LCSf.setDisable(false);
				PrintLCSf.setDisable(false);
			});
			LCSf.setOnAction(e -> {
				int[][] table = new int[size + 1][size + 1];
				map = new char[size + 1][size + 1];
				for (int i = 1; i < table.length; i++) {
					for (int j = 1; j < table[i].length; j++) {
						if (LED[i - 1] == power[j - 1]) {
							table[i][j] = table[i - 1][j - 1] + 1;
							map[i][j] = 'D';
						} else if (table[i][j - 1] > table[i - 1][j]) {
							table[i][j] = table[i][j - 1];
							map[i][j] = 'L';
						} else {
							table[i][j] = table[i - 1][j];
							map[i][j] = 'U';
						}
					}
				}
				txA.setVisible(true);

				for (int i = 0; i < table.length; i++) {
					for (int j = 0; j < table[i].length; j++) {
						txA.appendText(table[i][j] + "");
					}
					txA.appendText(" \n ");
				}

			});
			PrintLCSf.setOnAction(e -> {
				StringBuilder x = new StringBuilder();
				x=Print(map, power, LED.length, power.length);
				txA.appendText(x.toString());

			});

			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean checkduplicate(int[] a, int x) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == x)
				return false;
		}
		return true;
	}

	public StringBuilder Print(char[][] map, int[] x, int i, int j) {
		if (i == 0 || j == 0)
			return null;
		else {
			if (map[i][j] == 'D') {
				Print(map, x, i - 1, j - 1);
				s.append(x[j - 1]);
			} else {
				if (map[i][j] == 'L') {
					Print(map, x, i, j - 1);
				} else
					Print(map, x, i - 1, j);
			}

		}
		return s;
	}

	public static void main(String[] args) {
		launch(args);
	}
}