package org.xpdojo.quartz.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "booking")
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String id;

    @Column(name = "vin")
    private String vin;

    @Column(name = "shipping")
    private String shippingSchedule;

    @Column(name = "export_declaration_number")
    private String exportDeclarationNumber;

    @Column(name = "loading_deadline")
    private String loadingDeadline;

}
