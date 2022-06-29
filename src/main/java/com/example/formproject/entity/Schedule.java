package com.example.formproject.entity;

import com.example.formproject.FinalValue;
import com.example.formproject.dto.request.ScheduleRequestDto;
import com.example.formproject.repository.CropRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private String toDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Crop crop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    public void update(ScheduleRequestDto dto, CropRepository repository){
        this.startTime = LocalDateTime.parse(dto.getStartTime(), FinalValue.DAYTIME_FORMATTER);
        this.endTime = LocalDateTime.parse(dto.getEndTime(), FinalValue.DAYTIME_FORMATTER);
        this.crop = repository.findById(dto.getCrop()).orElseThrow(()->new IllegalArgumentException("작물 정보를 찾을 수 없습니다."));
        this.toDo = dto.getToDo();
    }
}
