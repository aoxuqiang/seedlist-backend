package com.example.seedlist.converter;

import com.example.seedlist.dto.MeetingDTO;
import com.example.seedlist.dto.UserMeetingDTO;
import com.example.seedlist.entity.Meeting;
import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.entity.MeetingInvite;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MeetingMapper {

    MeetingMapper MAPPER = Mappers.getMapper( MeetingMapper.class );

    MeetingDTO toDTO(Meeting meeting);

    List<MeetingDTO> toDTOList(List<Meeting> meetingList);


    UserMeetingDTO toUserMeeting(MeetingApply apply);

    UserMeetingDTO toUserMeeting(MeetingInvite invite);

    List<UserMeetingDTO> toApplyList(List<MeetingApply> applyList);

    List<UserMeetingDTO> toInviteList(List<MeetingInvite> inviteList);
}
