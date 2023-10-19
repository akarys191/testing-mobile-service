package com.testing.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "mobile_booking_journal")
public class MobileBookingJournal {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "testing_mobile_id", nullable = false)
    private TestingMobile testingMobile;

    @ManyToOne
    @JoinColumn(name = "tester_id", nullable = false)
    private Tester tester;

    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDateTime;

    @Column(name = "release_date")
    private LocalDateTime releaseDateTime;

}
