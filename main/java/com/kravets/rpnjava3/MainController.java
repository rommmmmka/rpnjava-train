package com.kravets.rpnjava3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class MainController {
    @FXML
    private Pane paneMain, paneAdmin, paneVisitor;
    @FXML
    private TextField textfieldRailcarModel, textfieldRailcarPassengersMax, textfieldRailcarComfortLevel;
    @FXML
    private TextField textfieldPassengerLastname, textfieldPassengerFirstname, textfieldPassengerPatronymic;
    @FXML
    private TextField textfieldBaggageWeight;
    @FXML
    private Label labelCurrentRailcar, labelCurrentPassenger, labelCurrentBaggage;
    @FXML
    private VBox boxRailcars, boxPassengers, boxBaggage;
    @FXML
    private Label labelCurrentRailcar2, labelCurrentPassenger2, labelCurrentBaggage2;
    @FXML
    private VBox boxRailcars2, boxPassengers2, boxBaggage2;
    @FXML
    private ChoiceBox<String> choiceRailcars, choicePassengers, choiceBaggage;

    private Train train = new Train();
    private Railcar currentRailcar = null;
    private Passenger currentPassenger = null;
    private Baggage currentBaggage = null;

    protected void adminClearRailcarInput() {
        textfieldRailcarModel.setText("");
        textfieldRailcarPassengersMax.setText("");
        textfieldRailcarComfortLevel.setText("");
    }

    protected void adminClearPassengerInput() {
        textfieldPassengerLastname.setText("");
        textfieldPassengerFirstname.setText("");
        textfieldPassengerPatronymic.setText("");
    }

    protected void adminClearBaggageInput() {
        textfieldBaggageWeight.setText("");
    }

    protected void adminRedrawLabels() {
        String railcarLabel = "Вагон не выбраны";
        String passengerLabel = "Пасажыр не выбраны";
        String baggageLabel = "Багаж не выбраны";
        if (currentRailcar != null)
            railcarLabel = currentRailcar.toString();
        if (currentPassenger != null)
            passengerLabel = currentPassenger.toString();
        if (currentBaggage != null) {
            List<Baggage> baggage = currentPassenger.getBaggage();
            for (int i = 0; i < baggage.size(); i++)
                if (baggage.get(i) == currentBaggage)
                    baggageLabel = String.format("Багаж №%d\nВага: %d кг.", i + 1, currentBaggage.getWeight());
        }
        labelCurrentRailcar.setText(railcarLabel);
        labelCurrentPassenger.setText(passengerLabel);
        labelCurrentBaggage.setText(baggageLabel);
    }

    protected void adminSetRailcarActive(Railcar railcar) {
        currentRailcar = railcar;
        currentPassenger = null;
        currentBaggage = null;
        textfieldRailcarModel.setText(railcar.getModel());
        textfieldRailcarPassengersMax.setText(Integer.toString(railcar.getPassengersMax()));
        textfieldRailcarComfortLevel.setText(Integer.toString(railcar.getComfortLevel()));
        adminRedrawLabels();
        adminRedrawRailcarsList();
        adminRedrawPassengersList();
        adminRedrawBaggageList();
    }

    protected void adminSetPassengerActive(Passenger passenger) {
        currentPassenger = passenger;
        currentBaggage = null;
        textfieldPassengerLastname.setText(passenger.getLastName());
        textfieldPassengerFirstname.setText(passenger.getFirstName());
        textfieldPassengerPatronymic.setText(passenger.getPatronymic());
        adminRedrawLabels();
        adminRedrawPassengersList();
        adminRedrawBaggageList();
    }

    protected void adminSetBaggageActive(Baggage baggage) {
        currentBaggage = baggage;
        textfieldBaggageWeight.setText(Integer.toString(baggage.getWeight()));
        adminRedrawLabels();
        adminRedrawBaggageList();
    }

    protected void adminRedrawRailcarsList() {
        boxRailcars.getChildren().clear();
        List<Railcar> railcars = train.getRailcars();
        for (int i = 0; i < railcars.size(); i++) {
            Button b = new Button();
            b.setText(railcars.get(i).toStringShort());
            int finalI = i;
            b.setOnMouseClicked(event -> adminSetRailcarActive(railcars.get(finalI)));
            boxRailcars.getChildren().add(b);
        }
    }

    protected void adminRedrawPassengersList() {
        boxPassengers.getChildren().clear();
        if (currentRailcar == null)
            return;
        List<Passenger> passengers = currentRailcar.getPassengers();
        for (int i = 0; i < passengers.size(); i++) {
            Button b = new Button();
            b.setText(passengers.get(i).toStringShort());
            int finalI = i;
            b.setOnMouseClicked(event -> adminSetPassengerActive(passengers.get(finalI)));
            boxPassengers.getChildren().add(b);
        }
    }

    protected void adminRedrawBaggageList() {
        boxBaggage.getChildren().clear();
        if (currentPassenger == null)
            return;
        List<Baggage> baggage = currentPassenger.getBaggage();
        for (int i = 0; i < baggage.size(); i++) {
            Button b = new Button();
            b.setText(String.format("Багаж №%d", i + 1));
            int finalI = i;
            b.setOnMouseClicked(event -> adminSetBaggageActive(baggage.get(finalI)));
            boxBaggage.getChildren().add(b);
        }
    }

    protected void adminClearPanels() {
        currentRailcar = null;
        currentPassenger = null;
        currentBaggage = null;
        adminRedrawLabels();
        adminRedrawRailcarsList();
        adminRedrawPassengersList();
        adminRedrawBaggageList();
        adminClearRailcarInput();
        adminClearPassengerInput();
        adminClearBaggageInput();
    }

    protected void userSetRailcarActive(Railcar railcar) {
        currentRailcar = railcar;
        currentPassenger = null;
        currentBaggage = null;
        userRedrawLabels();
        userRedrawRailcarsList();
        userRedrawPassengersList();
        userRedrawBaggageList();
    }

    protected void userSetPassengerActive(Passenger passenger) {
        currentPassenger = passenger;
        currentBaggage = null;
        userRedrawLabels();
        userRedrawPassengersList();
        userRedrawBaggageList();
    }

    protected void userSetBaggageActive(Baggage baggage) {
        currentBaggage = baggage;
        userRedrawLabels();
        userRedrawBaggageList();
    }

    @FXML
    protected void userRedrawRailcarsList() {
        boxRailcars2.getChildren().clear();
        List<Railcar> railcars = new ArrayList<>(train.getRailcars());
        if (Objects.equals(choiceRailcars.getValue(), "Па ўзроўні камфортнасці"))
            railcars.sort(Comparator.comparingInt(Railcar::getComfortLevel));
        if (Objects.equals(choiceRailcars.getValue(), "Па колькасці пасажыраў"))
            railcars.sort(Comparator.comparingInt(o -> -o.getPassengers().size()));
        for (int i = 0; i < railcars.size(); i++) {
            Button b = new Button();
            b.setText(railcars.get(i).toStringShort());
            int finalI = i;
            b.setOnMouseClicked(event -> userSetRailcarActive(railcars.get(finalI)));
            boxRailcars2.getChildren().add(b);
        }
    }

    @FXML
    protected void userRedrawPassengersList() {
        boxPassengers2.getChildren().clear();
        if (currentRailcar == null)
            return;
        List<Passenger> passengers = new ArrayList<>(currentRailcar.getPassengers());
        if (Objects.equals(choicePassengers.getValue(), "У алфавітным парадку"))
            passengers.sort((Comparator.comparing(Passenger::toString)));
        if (Objects.equals(choicePassengers.getValue(), "Па колькасці багажу"))
            passengers.sort(Comparator.comparingInt(o -> -o.getBaggage().size()));

        for (int i = 0; i < passengers.size(); i++) {
            Button b = new Button();
            b.setText(passengers.get(i).toStringShort());
            int finalI = i;
            b.setOnMouseClicked(event -> userSetPassengerActive(passengers.get(finalI)));
            boxPassengers2.getChildren().add(b);
        }
    }

    @FXML
    protected void userRedrawBaggageList() {
        boxBaggage2.getChildren().clear();
        if (currentPassenger == null)
            return;
        List<Baggage> baggage = currentPassenger.getBaggage();
        List<Pair<Integer, Baggage>> baggagePair = new ArrayList<>();
        for (int i = 0; i < baggage.size(); i++)
            baggagePair.add(i, new Pair<>(i, baggage.get(i)));

        if (Objects.equals(choiceBaggage.getValue(), "Па вазе"))
            baggagePair.sort(((o1, o2) -> o2.getValue().getWeight() - o1.getValue().getWeight()));
        for (int i = 0; i < baggage.size(); i++) {
            Button b = new Button();
            b.setText(String.format("Багаж №%d", baggagePair.get(i).getKey() + 1));
            int finalI = i;
            b.setOnMouseClicked(event -> userSetBaggageActive(baggage.get(baggagePair.get(finalI).getKey())));
            boxBaggage2.getChildren().add(b);
        }
    }

    protected void userRedrawLabels() {
        String railcarLabel = "Вагон не выбраны";
        String passengerLabel = "Пасажыр не выбраны";
        String baggageLabel = "Багаж не выбраны";
        if (currentRailcar != null)
            railcarLabel = currentRailcar.toString();
        if (currentPassenger != null)
            passengerLabel = currentPassenger.toString();
        if (currentBaggage != null) {
            List<Baggage> baggage = currentPassenger.getBaggage();
            for (int i = 0; i < baggage.size(); i++)
                if (baggage.get(i) == currentBaggage)
                    baggageLabel = String.format("Багаж №%d\nВага: %d кг.", i + 1, currentBaggage.getWeight());
        }
        labelCurrentRailcar2.setText(railcarLabel);
        labelCurrentPassenger2.setText(passengerLabel);
        labelCurrentBaggage2.setText(baggageLabel);
    }

    protected void userClearPanels() {
        currentRailcar = null;
        currentPassenger = null;
        currentBaggage = null;
        userRedrawLabels();
        userRedrawRailcarsList();
        userRedrawPassengersList();
        userRedrawBaggageList();
        ObservableList<String> railcarsFilters = FXCollections.observableArrayList("У парадку дабаўлення", "Па ўзроўні камфортнасці", "Па колькасці пасажыраў");
        choiceRailcars.setItems(railcarsFilters);
        choiceRailcars.setValue("У парадку дабаўлення");
        ObservableList<String> passengersFilters = FXCollections.observableArrayList("У парадку дабаўлення", "У алфавітным парадку", "Па колькасці багажу");
        choicePassengers.setItems(passengersFilters);
        choicePassengers.setValue("У парадку дабаўлення");
        ObservableList<String> baggageFilters = FXCollections.observableArrayList("У парадку дабаўлення", "Па вазе");
        choiceBaggage.setItems(baggageFilters);
        choiceBaggage.setValue("У парадку дабаўлення");
    }


    @FXML
    protected void onAdminButtonClick() {
        paneMain.setVisible(false);
        paneAdmin.setVisible(true);
        adminClearPanels();
    }

    @FXML
    protected void onUserButtonClick() {
        paneMain.setVisible(false);
        paneVisitor.setVisible(true);
        userClearPanels();
    }

    @FXML
    protected void onGoBackButtonClick() {
        paneMain.setVisible(true);
        paneAdmin.setVisible(false);
        paneVisitor.setVisible(false);
    }

    @FXML
    protected void adminOnAddRailcarButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры дабаўленні вагона");

        String model = textfieldRailcarModel.getText();
        if (Objects.equals(model, "") || Objects.equals(textfieldRailcarPassengersMax.getText(), "") || Objects.equals(textfieldRailcarComfortLevel.getText(), "")) {
            alert.setContentText("Запоўніце ўсе палі");
            alert.showAndWait();
            return;
        }
        try {
            int passengersMax = Integer.parseInt(textfieldRailcarPassengersMax.getText());
            int comfortLevel = Integer.parseInt(textfieldRailcarComfortLevel.getText());
            Railcar railcar = new Railcar(model, passengersMax, comfortLevel);
            train.addRailcar(railcar);
            adminClearRailcarInput();
            adminRedrawRailcarsList();
        } catch (NumberFormatException e) {
            alert.setContentText("Значэнні максімальнай колькасці пасажыраў і ўзроўню камфорту вагона павінны быць лікамі");
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnAddPassengerButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры дабаўленні пасажыра");

        if (currentRailcar == null) {
            alert.setContentText("Вагон не выбраны");
            alert.showAndWait();
            return;
        }
        String lastname = textfieldPassengerLastname.getText();
        String firstname = textfieldPassengerFirstname.getText();
        String patronymic = textfieldPassengerPatronymic.getText();
        if (Objects.equals(lastname, "") || Objects.equals(firstname, "") || Objects.equals(patronymic, "")) {
            alert.setContentText("Запоўніце ўсе палі");
            alert.showAndWait();
            return;
        }
        Passenger passenger = new Passenger(lastname, firstname, patronymic);
        try {
            currentRailcar.addPassenger(passenger);
            adminClearPassengerInput();
            adminRedrawPassengersList();
        } catch (RailcarNoFreeSpaceException e) {
            alert.setContentText("У вагоне няма свабодных месцаў");
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnAddBaggageButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры дабаўленні багажу");

        if (currentPassenger == null) {
            alert.setContentText("Пасажыр не выбраны");
            alert.showAndWait();
            return;
        }
        if (Objects.equals(textfieldBaggageWeight.getText(), "")) {
            alert.setContentText("Запоўніце поле");
            alert.showAndWait();
            return;
        }
        try {
            int weight = Integer.parseInt(textfieldBaggageWeight.getText());
            Baggage baggage = new Baggage(weight);
            currentPassenger.addBaggage(baggage);
            adminClearBaggageInput();
            adminRedrawBaggageList();
        } catch (Exception e) {
            alert.setContentText("Значэнне вагі павінна быць лікам");
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnEditRailcarButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры рэдагаванні інфармацыі аб вагоне");

        String model = textfieldRailcarModel.getText();
        if (Objects.equals(model, "") || Objects.equals(textfieldRailcarPassengersMax.getText(), "") || Objects.equals(textfieldRailcarComfortLevel.getText(), "")) {
            alert.setContentText("Запоўніце ўсе палі");
            alert.showAndWait();
            return;
        }
        try {
            int passengersMax = Integer.parseInt(textfieldRailcarPassengersMax.getText());
            int comfortLevel = Integer.parseInt(textfieldRailcarComfortLevel.getText());
            if (passengersMax < currentRailcar.getPassengers().size()) {
                alert.setContentText("Бягучая колькасць пасажыраў перавышае максімальную");
                alert.showAndWait();
                return;
            }
            currentRailcar.setModel(model);
            currentRailcar.setPassengersMax(passengersMax);
            currentRailcar.setComfortLevel(comfortLevel);
            adminClearRailcarInput();
            adminRedrawLabels();
            adminRedrawRailcarsList();
        } catch (NumberFormatException e) {
            alert.setContentText("Значэнні максімальнай колькасці пасажыраў і ўзроўню камфорту вагона павінны быць лікамі");
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnEditPassengerButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры рэдагаванні інфармацыі аб пасажыру");

        if (currentRailcar == null) {
            alert.setContentText("Вагон не выбраны");
            alert.showAndWait();
            return;
        }
        String lastname = textfieldPassengerLastname.getText();
        String firstname = textfieldPassengerFirstname.getText();
        String patronymic = textfieldPassengerPatronymic.getText();
        if (Objects.equals(lastname, "") || Objects.equals(firstname, "") || Objects.equals(patronymic, "")) {
            alert.setContentText("Запоўніце ўсе палі");
            alert.showAndWait();
            return;
        }
        currentPassenger.setLastName(lastname);
        currentPassenger.setFirstName(firstname);
        currentPassenger.setPatronymic(patronymic);
        adminClearPassengerInput();
        adminRedrawLabels();
        adminRedrawPassengersList();
    }

    @FXML
    protected void adminOnEditBaggageButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры рэдагаванні інфармацыі аб багажы");

        if (currentPassenger == null) {
            alert.setContentText("Пасажыр не выбраны");
            alert.showAndWait();
            return;
        }
        if (Objects.equals(textfieldBaggageWeight.getText(), "")) {
            alert.setContentText("Запоўніце поле");
            alert.showAndWait();
            return;
        }
        try {
            int weight = Integer.parseInt(textfieldBaggageWeight.getText());
            currentBaggage.setWeight(weight);
            adminClearBaggageInput();
            adminRedrawLabels();
            adminRedrawBaggageList();
        } catch (Exception e) {
            alert.setContentText("Значэнне вагі павінна быць лікам");
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnDeleteRailcarButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры выдаленні вагона");
        if (currentRailcar == null) {
            alert.setContentText("Вагон не выбраны");
            alert.showAndWait();
            return;
        }
        train.removeRailcar(currentRailcar);
        currentRailcar = null;
        currentPassenger = null;
        currentBaggage = null;
        adminRedrawLabels();
        adminRedrawRailcarsList();
        adminRedrawPassengersList();
        adminRedrawBaggageList();
    }

    @FXML
    protected void adminOnDeletePassengerButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры выдаленні пасажыра");
        if (currentPassenger == null) {
            alert.setContentText("Пасажыр не выбраны");
            alert.showAndWait();
            return;
        }
        currentRailcar.removePassenger(currentPassenger);
        currentPassenger = null;
        currentBaggage = null;
        adminRedrawLabels();
        adminRedrawPassengersList();
        adminRedrawBaggageList();
    }

    @FXML
    protected void adminOnDeleteBaggageButtonClick() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Памылка!");
        alert.setHeaderText("Узнікла памылка пры выдаленні багажу");
        if (currentBaggage == null) {
            alert.setContentText("Багаж не выбраны");
            alert.showAndWait();
            return;
        }
        currentPassenger.removeBaggage(currentBaggage);
        currentBaggage = null;
        adminRedrawLabels();
        adminRedrawBaggageList();
    }

    @FXML
    protected void adminOnFileSaveButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Захаваць у файл...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Belaruskaya Chyhunka File Format", "*.bebra"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        ContextMenu saveMenu = new ContextMenu();
        saveMenu.centerOnScreen();
        String currentPath = Paths.get("./").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        File fileName = fileChooser.showSaveDialog(saveMenu);
        try {
            if (fileName == null)
                throw new IOException("Empty path");
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(train);
            objectOutputStream.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Паспяхова!");
            alert.setHeaderText("Файл паспяхова захаваны");
            alert.setContentText("");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Памылка!");
            alert.setHeaderText("Памылка пры захаванні файла");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }

    @FXML
    protected void adminOnFileLoadButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузіць з файла...");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Belaruskaya Chyhunka File Format", "*.bebra"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        ContextMenu loadMenu = new ContextMenu();
        loadMenu.centerOnScreen();
        String currentPath = Paths.get("./").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        File fileName = fileChooser.showOpenDialog(loadMenu);
        try {
            if (fileName == null)
                throw new IOException("Empty path");
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            train = (Train) objectInputStream.readObject();
            objectInputStream.close();
            adminClearPanels();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Паспяхова!");
            alert.setHeaderText("Файл паспяхова загружаны");
            alert.setContentText("");
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Памылка!");
            alert.setHeaderText("Файл не знойдзены");
            alert.setContentText(e.toString());
            alert.showAndWait();
        } catch (IOException | ClassNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Памылка!");
            alert.setHeaderText("Памылка пры чытанні з файла");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
    }


}