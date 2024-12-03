package com.example.backend.Model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import com.example.backend.Enum.ClaimStatus;

@Entity
@Data
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private int claimId;

    @Column(name = "claim_name", nullable = false)
    private  String claimName;

    @Column(name = "claim_description")
    private String claimDescription;

    @Column(name = "submitted_date",nullable = false)
    private Date submittedDate;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_status")
    private ClaimStatus claimStatus;

    @Column(name = "claim_amount")
    private int claimAmount;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "registration_id", nullable = false)
    private PolicyRegistration registeredPolicy;
}
