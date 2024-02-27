package com.whaleal.zendesk.service.helpCenter;

public interface IExportGatherService {

    void exportTopicInfo();

    void importTopicInfo();

    void exportPostsInfo();

    void importPostsInfo();

    void exportUserSegment();

    void importUserSegment();

    void deleteUserSegment();

    void exportContentTag();

    void importContentTag();

    void deleteContentTag();


//    void exportUserSegmentInfo();
//
//    void importUserSegmentInfo();
    
}
