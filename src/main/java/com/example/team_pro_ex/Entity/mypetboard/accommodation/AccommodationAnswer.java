package com.example.team_pro_ex.Entity.mypetboard.accommodation;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AccommodationAnswer {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      public Long seq;

      @NotNull
      public String content;

      @Temporal(TemporalType.DATE)
      @CreatedDate
      public Date createDate;

      public String memberId;

      @ManyToOne(cascade = CascadeType.ALL)
      @JoinColumn(name = "Acc_seq")
      private Accommodation accommodation;

}
