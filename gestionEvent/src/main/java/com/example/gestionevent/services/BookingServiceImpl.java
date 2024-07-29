package com.example.gestionevent.services;


import com.example.gestionevent.model.*;
import com.example.gestionevent.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {
    Booking newBooking = new Booking();

    @Autowired
    BookingRepo bookingRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    FoodItemRepo foodItemRepository;

    @Autowired
    EquipmentRepo equipmentRepo;

    @Autowired
    EventRepo eventRepo;

    @Autowired
    VenueRepo venueRepo;

    int foodItemCost;
    int equipmentCost;
    int eventCost;
    int totalCost;
    String foodNames;
    String equipmentNames;


    @Override
    public Booking rectifyBooking(Booking localBooking) {
        // Validate if the date is available before proceeding
        if (!isAvailable(localBooking)) {
            throw new RuntimeException("The selected date is already taken for this venue.");
        }

        User user = userRepo.findById(localBooking.getUserName()).orElseThrow(() -> new RuntimeException("User not found"));
        localBooking.setUser(user);

        this.foodItemCost = 0;
        this.equipmentCost = 0;
        this.eventCost = 0;
        this.totalCost = 0;
        this.foodNames = " ";
        this.equipmentNames = " ";

        // Get array of selected food Item Id in string format
        String[] selFood = localBooking.getSelectedFoodItems().trim().split(",");

        // Get food Items Cost and Names
        for (String f : selFood) {
            if (!(f.equals(""))) {
                int fId = Integer.parseInt(f);
                FoodItem food = foodItemRepository.findById(fId).get();
                int ownCost = food.getFoodItemCost();
                this.foodItemCost = this.foodItemCost + ownCost;
                this.foodNames = this.foodNames + " " + food.getFoodItemName();
            }
        }

        // Get array of selected Equipments id in string format
        String[] selEquip = localBooking.getSelectedEquipments().split(",");

        // Get Equipment Cost and Names
        for (String e : selEquip) {
            if (!(e.equals(""))) {
                int eId = Integer.parseInt(e);
                Equipment equip = equipmentRepo.findById(eId).get();
                int ownCost = equip.getEquipmentCost();
                this.equipmentCost = this.equipmentCost + ownCost;
                this.equipmentNames = this.equipmentNames + " " + equip.getEquipmentName();
            }
        }

        // Get Event Cost
        String eventName = localBooking.getEventName();
        List<Event> events = eventRepo.findByVenueId(localBooking.getVenueId());
        for (Event e : events) {
            if (eventName.equals(e.getEventName())) {
                this.eventCost = e.getEventCost();
            }
        }

        // Final Food Item and Total Cost
        this.foodItemCost = this.foodItemCost * localBooking.getGuestCount();
        this.totalCost = this.foodItemCost + this.equipmentCost + this.eventCost;

        Booking newBooking = new Booking();
        newBooking.setBookingId(localBooking.getBookingId());
        newBooking.setDate(localBooking.getDate());
        newBooking.setGuestCount(localBooking.getGuestCount());
        newBooking.setEventName(localBooking.getEventName());
        newBooking.setEquipmentName(this.equipmentNames);
        newBooking.setFoodItemName(this.foodNames);
        newBooking.setEventCost(this.eventCost);
        newBooking.setEquipmentCost(this.equipmentCost);
        newBooking.setFoodItemCost(this.foodItemCost);
        newBooking.setTotalCost(this.totalCost);
        newBooking.setPaymentStatus(localBooking.getPaymentStatus());
        newBooking.setUser(userRepo.findById(localBooking.getUserName()).get());
        newBooking.setVenue(venueRepo.findById(localBooking.getVenueId()).get());
        newBooking.setDelStatus(1);
        newBooking.setVenueId(localBooking.getVenueId());
        newBooking.setUserName(localBooking.getUserName());

        return bookingRepo.save(newBooking);
    }

    List<BookingDetail> upcomingBookings;
    List<BookingDetail> historyBookings;

    @Override
    public List<BookingDetail> getBookingDetailByOrganizerId(String userName, String tense) {

        List<Venue> venueList = venueRepo.findByUserName(userName);

        this.upcomingBookings = new ArrayList<>();
        this.historyBookings = new ArrayList<>();

        for (Venue v : venueList) {

            Venue venue = venueRepo.findById(v.getVenueId()).get();

            List<Booking> bookingList = bookingRepo.findByVenue(venue);

            List<BookingDetail> localList = copyDataFromBookingToBookingDetail(bookingList);

            for (BookingDetail bd : localList) {
                // int delStatus = bd.getDelStatus();
                LocalDate todayDate = LocalDate.now();
                LocalDate bookingDate = sqlToLocalDateConverter(bd.getDate());
                String paymentStatus = bd.getPaymentStatus();
                if ((todayDate.isBefore(bookingDate) || todayDate.isEqual(bookingDate))
                    && paymentStatus.equals("Processed")) {

                    this.upcomingBookings.add(bd);
                } else if (todayDate.isAfter(bookingDate) && paymentStatus.equals("Processed")) {
                    /* && (delStatus==1 || delStatus==11 || delStatus==110 || delStatus==111 ) */
                    this.historyBookings.add(bd);

                }
            }
        }
        if (tense.equals("Past")) {
            return this.historyBookings;
        } else {
            return this.upcomingBookings;
        }

    }

    public LocalDate sqlToLocalDateConverter(Date sqlDate) {
        return Date.valueOf(sqlDate.toString()).toLocalDate();
    }


    Boolean flag;


    @Override
    public Boolean isAvailableForPayment(int tempBookingId) {
        flag = true;
        Booking b = bookingRepo.findById(tempBookingId).get();

        Date bDate = b.getDate();
        int venueId = b.getVenue().getVenueId();
        List<String> alreadyBookedDates = this.getUpcomingBookedDates(venueId);
        String stringBDate = bDate.toString();
        for (String bookedDate : alreadyBookedDates) {

            String stringBookedDate = bookedDate;
            if (stringBookedDate.equals(stringBDate)) {
                this.flag = false;
            }
        }
        return this.flag;
    }

    @Override
    public Booking getBooking(int bookingId) {
        return bookingRepo.findById(bookingId).get();
    }

    @Override
    public List<BookingDetail> getBookings() {

        List<Booking> bookingList = (List<Booking>) bookingRepo.findAll();

        return copyDataFromBookingToBookingDetail(bookingList);
    }

    private List<BookingDetail> copyDataFromBookingToBookingDetail(List<Booking> bookingList) {

        List<BookingDetail> localList = new ArrayList<BookingDetail>();

        for (Booking b : bookingList) {

            BookingDetail bookingD = copySingleBookingToDetails(b);

            localList.add(bookingD);
        }
        return localList;
    }

    private BookingDetail copySingleBookingToDetails(Booking b) {
        BookingDetail bkDetail = new BookingDetail();
        bkDetail.setBookingId(b.getBookingId());
        bkDetail.setUserName(b.getUser().getUserName());
        bkDetail.setVenueId(b.getVenue().getVenueId());
        bkDetail.setDate(b.getDate());
        bkDetail.setGuestCount(b.getGuestCount());
        bkDetail.setEventName(b.getEventName());
        bkDetail.setEquipmentName(b.getEquipmentName());
        bkDetail.setFoodItemName(b.getFoodItemName());
        bkDetail.setEquipmentCost(b.getEquipmentCost());
        bkDetail.setFoodItemCost(b.getFoodItemCost());
        bkDetail.setEventCost(b.getEventCost());
        bkDetail.setTotalCost(b.getTotalCost());
        bkDetail.setPaymentStatus(b.getPaymentStatus());
        bkDetail.setFirstName(b.getUser().getUserFirstName());
        bkDetail.setLastName(b.getUser().getUserLastName());
        bkDetail.setEmail(b.getUser().getUserEmail());
        bkDetail.setPhoneNumber(b.getUser().getUserNumber());
        bkDetail.setVenueName(b.getVenue().getVenueName());
        bkDetail.setVenuePlace(b.getVenue().getVenuePlace());
        bkDetail.setVenueContact(b.getVenue().getVenueContact());
        bkDetail.setDelStatus(b.getDelStatus());
        return bkDetail;
    }

    // Service to get list of bookings for USER//####
    @Override
    public List<BookingDetail> getBookingsByUserId(String userName) {

        User user = userRepo.findById(userName).get();
        List<Booking> bookingList = bookingRepo.findByUser(user);

        return copyDataFromBookingToBookingDetail(bookingList);
    }

    // Service to get Booking Detail by Booking ID//####
    @Override
    public BookingDetail bookingDetail(int bookingId) {
        Booking booking = bookingRepo.findById(bookingId).get();
        return copySingleBookingToDetails(booking);
    }

    // Service to Make payment request//####
    @Override
    public int payment(int bookingId) {


        if (isAvailableForPayment(bookingId)) {

            Booking booking = bookingRepo.findById(bookingId).get();
            booking.setPaymentStatus("Processed");
            booking.setDelStatus(111);

            bookingRepo.save(booking);

            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int deleteBooking(@PathVariable("bookingId") int bookingId) {
        bookingRepo.deleteById(bookingId);
        return 1;
    }

    @Override
    public int checkActiveBookings(int venueId) {
        int i = 0;

        Venue venue = venueRepo.findById(venueId).get();
        List<Booking> bookings = bookingRepo.findByVenue(venue);

        List<BookingDetail> localList = copyDataFromBookingToBookingDetail(bookings);
        for (BookingDetail bd : localList) {
            LocalDate todayDate = LocalDate.now();
            LocalDate bookingDate = sqlToLocalDateConverter(bd.getDate());
            String paymentStatus = bd.getPaymentStatus();
            if ((todayDate.isBefore(bookingDate) || todayDate.isEqual(bookingDate))
                && paymentStatus.equals("Processed")) {
                i = 1;
            }
        }
        return i;
    }

    List<Date> requiredDates;


    @Override
    public Boolean isAvailable(Booking tempBooking) {
        LocalDate today = LocalDate.now();
        LocalDate bookingDate = tempBooking.getDate().toLocalDate();

        // If the booking date is before today, it's invalid
        if (bookingDate.isBefore(today)) {
            return false;
        }

        Venue venue = venueRepo.findById(tempBooking.getVenueId()).get();
        List<Booking> bookings = bookingRepo.findByVenue(venue);

        for (Booking booking : bookings) {
            if (booking.getDate().equals(tempBooking.getDate())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getUpcomingBookedDates(int venueId) {
        List<String> requiredDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        Venue venue = venueRepo.findById(venueId).get();
        List<Booking> bookings = bookingRepo.findBookingByVenueAndPaymentStatusOrderByDate(venue, "Processed");

        for (Booking booking : bookings) {
            LocalDate bookedDate = booking.getDate().toLocalDate();
            if (today.isBefore(bookedDate) || today.isEqual(bookedDate)) {
                requiredDates.add(booking.getDate().toString());
            }
        }
        return requiredDates;
    }
    @Override
    public List<Date> getAllUpcomingBookedDates() {
        List<Date> requiredDates = new ArrayList<>();
        LocalDate today = LocalDate.now();
        List<Booking> bookings = (List<Booking>) bookingRepo.findAll();

        for (Booking booking : bookings) {
            LocalDate bookedDate = booking.getDate().toLocalDate();
            if (today.isBefore(bookedDate) || today.isEqual(bookedDate)) {
                requiredDates.add(booking.getDate());
            }
        }
        return requiredDates;
    }


}
