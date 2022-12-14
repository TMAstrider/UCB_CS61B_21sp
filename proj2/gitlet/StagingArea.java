package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StagingArea implements Serializable {
    private Map<String, String> tempTrackedFile;

    public StagingArea() {
        tempTrackedFile = new HashMap<>();
    }

    public void saveAddStage() {
        Utils.writeObject(Repository.addStagingArea, this);
    }


    public void saveRmStage() {
        Utils.writeObject(Repository.rmStagingArea, this);
    }
    public void addFile(String key, String value) {
        tempTrackedFile.put(key, value);
    }

    public void rmFile(Blob rmFile) {
        tempTrackedFile.remove(rmFile.getFilePathName());
    }

    public Map<String, String> getStageMap() {
        return tempTrackedFile;
    }
    public void rmFile(File rmFile) {
        tempTrackedFile.remove(rmFile.getPath());
    }

    public void clear() {
        tempTrackedFile.clear();
    }

    public boolean isExist(Blob stageBlob) {
        return tempTrackedFile.containsValue(stageBlob.getBlobId());
    }

    public boolean isEmpty() {
        return tempTrackedFile.isEmpty();
    }

    public boolean isExistFileName(File fileName) {
        return tempTrackedFile.containsKey(fileName.getPath());
    }


}
