package View;

import javax.swing.JOptionPane;
import Controller.Jarvis;
import Model.Model;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainApp extends Application {

	protected static final Window MainApp = null;

	private Jarvis jarvis = Jarvis.getJarvis();

	private String selectedList = "Computers";

	private ObservableList<Model> getModels() {
		ObservableList<Model> model = FXCollections.observableArrayList();
		model = desiserials(model, jarvis.getArray(selectedList));
		return model;
	}

	VBox vbox = new VBox();
	Scene firstScene = new Scene(vbox);

	private ObservableList<Model> desiserials(ObservableList<Model> value, Model[] array) {
		for (Model model : array) {
			if (model != null) {
				value.add(model);
			}
		}
		return value;
	}

	@Override
	public void start(Stage primaryStage) {
		Label label = new Label("Перечень устройств:");
		label.setFont(new Font("Arial", 14));// Надо по середине

		TableView<Model> table = new TableView<>(getModels());
		table.setEditable(true);
		TableColumn<Model, String> manufacturer = new TableColumn<>("Производитель");
		manufacturer.setMinWidth(200);
		manufacturer.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
		manufacturer.setCellFactory(TextFieldTableCell.<Model>forTableColumn());
		manufacturer.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Model, String>>() {
			@Override
			public void handle(CellEditEvent<Model, String> event) {
				if (event != null) {
					((Model) event.getTableView().getItems().get(event.getTablePosition().getRow()))
							.setManufacturer(event.getNewValue());
				}

			}
		});

		TableColumn<Model, String> name = new TableColumn<>("Модель");
		name.setMinWidth(200);
		name.setCellValueFactory(new PropertyValueFactory<>("numberModel"));
		name.setCellFactory(TextFieldTableCell.<Model>forTableColumn());
		name.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Model, String>>() {
			@Override
			public void handle(CellEditEvent<Model, String> event) {
				((Model) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setNumberModel(event.getNewValue());

			}
		});

		TableColumn<Model, String> prace = new TableColumn<>("Цена");
		prace.setMinWidth(200);
		prace.setCellValueFactory(new PropertyValueFactory<>("price"));
		prace.setCellFactory(TextFieldTableCell.<Model>forTableColumn());
		prace.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Model, String>>() {
			@Override
			public void handle(CellEditEvent<Model, String> event) {
				((Model) event.getTableView().getItems().get(event.getTablePosition().getRow()))
						.setPrice(event.getNewValue());

			}
		});

		table.getColumns().add(manufacturer);
		table.getColumns().add(name);
		table.getColumns().add(prace);

		Menu menu = new Menu("Файл");
		MenuItem menuOpen = new MenuItem("Открыть");
		Menu subMenu = new Menu("Операции");
		MenuItem menuAdd = new MenuItem("Добавить");
		MenuItem menuSearch = new MenuItem("Найти");
		MenuItem menuDelete = new MenuItem("Удалить");

		Menu statisticsMenu = new Menu("Статистика");
		Menu circularChart = new Menu("Круговой график");
		MenuItem manufacturerStat = new MenuItem("График по производителю");
		MenuItem praceStat = new MenuItem("График по цене");
		circularChart.getItems().add(manufacturerStat);
		circularChart.getItems().add(praceStat);
		Menu lineChart = new Menu("Линейный график");
		MenuItem manufacturerStat1 = new MenuItem("График по производителю");
		MenuItem praceStat1 = new MenuItem("График по цене");
		lineChart.getItems().add(manufacturerStat1);
		lineChart.getItems().add(praceStat1);
		statisticsMenu.getItems().add(circularChart);
		statisticsMenu.getItems().add(lineChart);
		Menu devices = new Menu("Устройства");
		MenuItem computer = new MenuItem("Компьютеры");
		MenuItem keyboard = new MenuItem("Клавиатуры");
		MenuItem mouse = new MenuItem("Мышки");
		devices.getItems().add(computer);
		devices.getItems().add(keyboard);
		devices.getItems().add(mouse);
		MenuItem menuExit = new MenuItem("Выйти");
		menu.getItems().add(menuOpen);
		subMenu.getItems().add(menuAdd);
		subMenu.getItems().add(menuSearch);
		subMenu.getItems().add(menuDelete);
		menu.getItems().add(subMenu);
		menu.getItems().add(menuExit);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu);
		menuBar.getMenus().add(statisticsMenu);
		menuBar.getMenus().add(devices);

		manufacturerStat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				PieChart pieChart = new PieChart();
				Button back = new Button("Назад");
				back.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						primaryStage.setScene(firstScene);
						primaryStage.show();
						primaryStage.setTitle("Магазин устройств");
					}
				});
				primaryStage.setTitle("Круговой график по производителю");
				PieChart.Data slice1 = new PieChart.Data("Компьютеры", jarvis.getArrayComps().length);
				PieChart.Data slice2 = new PieChart.Data("Клавиатуры", jarvis.getArrayKeyboards().length);
				PieChart.Data slice3 = new PieChart.Data("Мышки", jarvis.getArrayMouses().length);

				pieChart.getData().add(slice1);
				pieChart.getData().add(slice2);
				pieChart.getData().add(slice3);

				VBox vbox = new VBox(pieChart, back);
				vbox.setPadding(new Insets(20, 0, 0, 10));
				Scene scene = new Scene(vbox, 300, 200);
				primaryStage.setScene(scene);
				primaryStage.show();

			}
		});
		praceStat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {///////////// цена надо перевести в инт массив
				PieChart pieChart = new PieChart();
				Button back = new Button("Назад");
				back.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						primaryStage.setScene(firstScene);
						primaryStage.show();
						primaryStage.setTitle("Магазин устройств");
					}
				});
				primaryStage.setTitle("Кругово график по цене");
				PieChart.Data slice1 = new PieChart.Data("Компьютеры", getValue(jarvis.getArrayComps()));
				PieChart.Data slice2 = new PieChart.Data("Клавиатуры", getValue(jarvis.getArrayKeyboards()));
				PieChart.Data slice3 = new PieChart.Data("Мышки", getValue(jarvis.getArrayMouses()));

				pieChart.getData().add(slice1);
				pieChart.getData().add(slice2);
				pieChart.getData().add(slice3);

				VBox vbox = new VBox(pieChart, back);
				vbox.setPadding(new Insets(20, 0, 0, 10));
				Scene scene = new Scene(vbox, 300, 200);
				primaryStage.setScene(scene);
				primaryStage.show();
			}

		});
		manufacturerStat1.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void handle(ActionEvent event) {
				Button back = new Button("Назад");
				back.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						primaryStage.setScene(firstScene);
						primaryStage.show();
						primaryStage.setTitle("Магазин устройств");
					}
				});
				primaryStage.setTitle("Линейный график по производителю");
				CategoryAxis xAxis = new CategoryAxis();
				xAxis.setLabel("Устройства");
				NumberAxis yAxis = new NumberAxis();
				yAxis.setLabel("Количество");
				BarChart barChart = new BarChart(xAxis, yAxis);
				XYChart.Series dataSeries1 = new XYChart.Series();
				dataSeries1.getData().add(new XYChart.Data("Компьютеры", jarvis.getArrayComps().length));
				dataSeries1.getData().add(new XYChart.Data("Клавиатуры", jarvis.getArrayKeyboards().length));
				dataSeries1.getData().add(new XYChart.Data("Мышки", jarvis.getArrayMouses().length));
				barChart.getData().add(dataSeries1);
				HBox hbox = new HBox(back);
				hbox.setPadding(new Insets(10, 0, 0, 20));
				VBox vbox = new VBox(barChart, hbox);
				vbox.setPadding(new Insets(20, 0, 0, 10));
				Scene scene = new Scene(vbox, 400, 200);
				primaryStage.setScene(scene);
				primaryStage.show();

			}
		});
		praceStat1.setOnAction(new EventHandler<ActionEvent>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public void handle(ActionEvent event) {
				Button back = new Button("Назад");
				back.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						primaryStage.setScene(firstScene);
						primaryStage.show();
						primaryStage.setTitle("Магазин устройств");
					}
				});
				primaryStage.setTitle("Линейный график по цене");
				CategoryAxis xAxis = new CategoryAxis();
				xAxis.setLabel("Устройства");
				NumberAxis yAxis = new NumberAxis();
				yAxis.setLabel("Сумма");
				BarChart barChart = new BarChart(xAxis, yAxis);
				XYChart.Series dataSeries1 = new XYChart.Series();
				dataSeries1.getData().add(new XYChart.Data("Компьютеры", getValue(jarvis.getArrayComps())));
				dataSeries1.getData().add(new XYChart.Data("Клавиатуры", getValue(jarvis.getArrayKeyboards())));
				dataSeries1.getData().add(new XYChart.Data("Мышки", getValue(jarvis.getArrayMouses())));
				barChart.getData().add(dataSeries1);
				HBox hbox = new HBox(back);
				hbox.setPadding(new Insets(10, 0, 0, 20));
				VBox vbox = new VBox(barChart, hbox);
				vbox.setPadding(new Insets(20, 0, 0, 10));
				Scene scene = new Scene(vbox, 400, 200);
				primaryStage.setScene(scene);
				primaryStage.show();

			}
		});

		TextArea textManufacturer = new TextArea();
		textManufacturer.setPromptText("Производитель");
		textManufacturer.setMaxWidth(120);
		textManufacturer.setMaxHeight(10);
		textManufacturer.setDisable(true);

		TextArea textName = new TextArea();
		textName.setPromptText("Модель");
		textName.setMaxWidth(120);
		textName.setMaxHeight(10);
		textName.setDisable(true);

		TextArea textPrice = new TextArea();
		textPrice.setPromptText("Цена");
		textPrice.setMaxWidth(120);
		textPrice.setMaxHeight(10);
		textPrice.setDisable(true);

		Button add = new Button("Добавить");
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StringBuilder message = new StringBuilder();
				boolean isDataFilled = true;
				if (textManufacturer.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели производителя!\n");
					isDataFilled = false;
				}
				if (textName.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели модель!\n");
					isDataFilled = false;
				}
				if (textPrice.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели цену!\n");
					isDataFilled = false;
				}
				if (isDataFilled) {
					jarvis.addModel(new Model(textManufacturer.getText(), textName.getText(), textPrice.getText()));
					message.append("Устройство " + textManufacturer.getText() + " " + " " + textName.getText()
							+ textPrice.getText() + " успешно добавлено!");
					textManufacturer.setText("");
					textName.setText("");
					textPrice.setText("");
					table.setItems(getModels());

				}
				JOptionPane.showMessageDialog(null, message, "Дабовление устройства", JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuSearch.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StringBuilder message = new StringBuilder();
				if (textManufacturer.getText().length() > 0) {
					message.append(jarvis.search(textManufacturer.getText()));
					textManufacturer.setText("");
					textName.setText("");
					textPrice.setText("");
				} else {
					message.append("Ошибка: Вы не ввели устройство!");
				}

				JOptionPane.showMessageDialog(null, message, "Поиск устройства по производителю",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		Button search = new Button("Найти"); // создать поиск
		search.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StringBuilder message = new StringBuilder();
				if (textManufacturer.getText().length() > 0) {
					message.append(jarvis.search(textManufacturer.getText()));
					textManufacturer.setText("");
					textName.setText("");
					textPrice.setText("");
				} else {
					message.append("Ошибка: Вы не ввели устройство!");
				}

				JOptionPane.showMessageDialog(null, message, "Поиск устройства по производителю",
						JOptionPane.PLAIN_MESSAGE);
			}
		});
		menuAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				StringBuilder message = new StringBuilder();
				boolean isDataFilled = true;
				if (textManufacturer.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели производителя!\n");
					isDataFilled = false;
				}
				if (textName.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели модель!\n");
					isDataFilled = false;
				}
				if (textPrice.getText().length() == 0) {
					message.append("Ошибка: Вы не ввели цену!\n");
					isDataFilled = false;
				}
				if (isDataFilled) {
					jarvis.addModel(new Model(textManufacturer.getText(), textName.getText(), textPrice.getText()));
					message.append("Устройство " + textManufacturer.getText() + " " + " " + textName.getText()
							+ textPrice.getText() + " успешно добавлено!");
					textManufacturer.setText("");
					textName.setText("");
					textPrice.setText("");
					table.setItems(getModels());

				}
				JOptionPane.showMessageDialog(null, message, "Дабовление устройства", JOptionPane.PLAIN_MESSAGE);
			}
		});

		Button delete = new Button("Удалить");
		delete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int row = table.getSelectionModel().getSelectedIndex();//
				jarvis.delete(table.getSelectionModel().getSelectedItem());
				table.getItems().remove(row);
				table.setItems(getModels());
			}
		});
		menuDelete.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int row = table.getSelectionModel().getSelectedIndex();//
				jarvis.delete(table.getSelectionModel().getSelectedItem());
				table.getItems().remove(row);
				table.setItems(getModels());
			}
		});

		menuExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.exit();
				System.exit(0);

			}
		});
		menuOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				fileChooser.showOpenDialog(MainApp);

			}
		});

		HBox hbox1 = new HBox();
		hbox1.setSpacing(5);
		hbox1.setPadding(new Insets(10, 0, 0, 10));
		hbox1.getChildren().addAll(textManufacturer, textName, textPrice, add, search, delete);

		vbox.setSpacing(5);
		vbox.setPadding(new Insets(10, 0, 0, 10));
		vbox.getChildren().addAll(menuBar, label, table, hbox1);

		primaryStage.setTitle("Магазин устройств");
		primaryStage.setWidth(630);
		primaryStage.setHeight(560);
		primaryStage.setScene(firstScene);
		primaryStage.show();

		computer.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				selectedList = computer.getText();
				table.setItems(getModels());
				textManufacturer.setDisable(false);
				textName.setDisable(false);
				textPrice.setDisable(false);
			}
		});

		keyboard.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				selectedList = keyboard.getText();
				table.setItems(getModels());
				textManufacturer.setDisable(false);
				textName.setDisable(false);
				textPrice.setDisable(false);
			}
		});

		mouse.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				selectedList = mouse.getText();
				table.setItems(getModels());
				textManufacturer.setDisable(false);
				textName.setDisable(false);
				textPrice.setDisable(false);
			}
		});

	}

	private double getValue(Model[] arrayComps) {
		double sum = 0.0;
		for (Model model : arrayComps) {
			sum = Double.parseDouble(model.getPrice());
		}
		return sum;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
