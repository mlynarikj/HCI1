package clinic.appointments;

import DBAccess.ClinicDBAccess;
import clinic.Constants;
import clinic.common.MainWindowController;
import clinic.PersonWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import jfxtras.scene.control.LocalDatePicker;
import model.*;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.MINUTES;

public class AddAppointment extends MainWindowController {

    @FXML
    private ChoiceBox<PersonWrapper> patient;
    @FXML
    private ChoiceBox<PersonWrapper> doctor;
    @FXML
    private LocalDatePicker week;
    @FXML
    private ChoiceBox<String> slot;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }

    @Override
    public void initClinic(ClinicDBAccess clinicDBAccess) {
        super.initClinic(clinicDBAccess);
        patient.setItems(FXCollections.observableList(PersonWrapper.convert(clinicDBAccess.getPatients())));
        patient.getSelectionModel().selectFirst();
        doctor.setItems(FXCollections.observableList(PersonWrapper.convert(clinicDBAccess.getDoctors())));
        doctor.getSelectionModel().selectFirst();
        doctor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            week.disabledLocalDates().clear();
            week.disabledLocalDates().addAll(findUnavailableDates(new LocalDatePicker.LocalDateRange(week.getDisplayedLocalDate().withDayOfMonth(1), week.getDisplayedLocalDate().withDayOfMonth(week.getDisplayedLocalDate().lengthOfMonth())), newValue));
        });
        week.setLocalDateRangeCallback((range) -> {
            week.disabledLocalDates().clear();
            week.disabledLocalDates().addAll(findUnavailableDates(range, doctor.getValue()));
            return null;
        });
        week.localDateProperty().addListener((observable, oldValue, newValue) -> {
            ArrayList<LocalTime> app = clinicDBAccess.getDoctorAppointments(doctor.getValue().getPerson().getIdentifier())
                    .stream()
                    .filter(p -> LocalDate.from(p.getAppointmentDateTime()).isEqual(newValue))
                    .map(p -> LocalTime.from(p.getAppointmentDateTime()))
                    .collect(Collectors.toCollection(ArrayList::new));
            ObservableList<String> available = FXCollections.observableArrayList();
            long time = ((Doctor) doctor.getValue().getPerson()).getVisitStartTime().until(((Doctor) doctor.getValue().getPerson()).getVisitEndTime(), MINUTES);
            for (long i = 0; i < time; i += 15) {
                LocalTime t = ((Doctor) doctor.getValue().getPerson()).getVisitStartTime().plusMinutes(i);
                if (!app.contains(t) && (!newValue.isEqual(LocalDate.now()) || t.isAfter(LocalTime.now()))) {
                    available.add(t.format(DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
            slot.setItems(available);
            slot.getSelectionModel().selectFirst();
        });
    }

    public void save(MouseEvent mouseEvent) {
        if (week.getLocalDate() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid appointment");
            alert.setContentText("Please select a date for the appointment from the calendar.");
            alert.show();
            return;
        }
        clinicDBAccess.getAppointments()
                .add(new Appointment(LocalDateTime.of(week.getLocalDate(),LocalTime.parse(slot.getValue(),DateTimeFormatter.ofPattern("HH:mm"))), (Doctor) doctor.getValue().getPerson(), (Patient) patient.getValue().getPerson()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment created");
        alert.setContentText("An appointment was created.");
        alert.show();
        loadScene(Constants.APPOINTMENTS_LIST);
    }

    public void cancel(MouseEvent mouseEvent) {
        loadScene(Constants.APPOINTMENTS_LIST);
    }

    private List<LocalDate> findUnavailableDates(LocalDatePicker.LocalDateRange range, PersonWrapper newValue) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int start_week = range.getStartLocalDate().get(weekFields.weekOfWeekBasedYear());
        int end_week = range.getEndLocalDate().get(weekFields.weekOfWeekBasedYear());

        LocalDate start = range.getStartLocalDate().withDayOfMonth(1);
        LocalDate end = range.getEndLocalDate();
        final int days = (int) start.until(end.plusDays(1), ChronoUnit.DAYS);
        List<LocalDate> result = Stream.iterate(start, d -> d.plusDays(1))
                .limit(days)
                .collect(Collectors.toList());
        Doctor doc = (Doctor) newValue.getPerson();
        List<LocalDate> available = new ArrayList<>();

        long maxAppointments = doc.getVisitStartTime().until(doc.getVisitEndTime(), MINUTES) / 15;
        Map<LocalDate, Long> appPerDay = clinicDBAccess.getDoctorAppointments(doc.getIdentifier()).stream()
                .map(p -> LocalDate.from(p.getAppointmentDateTime()))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<LocalDate> collect = appPerDay.keySet().stream().filter(p -> appPerDay.get(p) == maxAppointments).collect(Collectors.toList());
        for (int i = start_week; i <= end_week; i++) {
            for (Days day : doc.getVisitDays()) {
                LocalDate curr = range.getStartLocalDate().plusWeeks(i - start_week).with(DayOfWeek.of(day.ordinal() + 1));
                if (!collect.contains(curr) && !curr.isBefore(LocalDate.now()) && (curr.isAfter(LocalDate.now()) || doc.getVisitEndTime().isAfter(LocalTime.now()))) {
                    available.add(curr);
                }
            }
        }
        result.removeAll(available);
        return result;

    }

}
