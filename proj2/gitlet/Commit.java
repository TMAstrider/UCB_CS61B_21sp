package gitlet;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/** Represents a gitlet commit object.
 *  does at a high level.
 *
 *  @author TMAstrider
 */
public class Commit implements Serializable {
    /**
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    /** The message of this Commit. */
    private String message;
    private Date timestamp;
    private List<String> parents;
    private String id;
    private String strTimestamp;

    /**
     * The tracked file Map with
     * filePath as key
     * file Sha1 value as value
     * */
    private Map<String, String> filePathId = new HashMap<>();
    private File savedFileName;

    public Commit(String commitMessage) {

    }
    public Commit(String commitMessage, List<String> parent) {
        this.message = commitMessage;
        this.parents = parent;
        this.timestamp = new Date();
        id = generateId();
        savedFileName = getFilePath(id);
        strTimestamp = getTimestamp(timestamp);
    }

    public Commit() {
        message = "initial commit";
        parents = new ArrayList<>();
        timestamp = new Date(0);
        id = generateId();
        savedFileName = getFilePath(id);
        strTimestamp = getTimestamp(timestamp);
    }

    public void save() {
        if (!savedFileName.getParentFile().exists()) {
            savedFileName.getParentFile().mkdir();
        }
        Utils.writeObject(savedFileName, this);
    }
    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public String getTimestamp(Date referredTimestamp) {
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z", Locale.US);
        return dateFormat.format(referredTimestamp);
    }

    public String getStrTimestamp() {
        return strTimestamp;
    }

    public String generateId() {
        return Utils.sha1(message.toString(), timestamp.toString(), getParent().toString());
    }

    public File getFilePath(String fileId) {
        return Utils.join(Repository.COMMIT, fileId);
    }

    public List<String> getParent() {
        return parents;
    }

    public List<String> getParentsList() {
        return parents;
    }

    public Map<String, String> getMap() {
        return filePathId;
    }

    public void saveParent() {
        parents.add(id);
    }

    public void saveCommitMessage(String savedMessage) {
        message = savedMessage;
    }

    public void addStagedFile(StagingArea stage) {
        Map<String, String> addedStageMap = stage.getStageMap();
        for (Map.Entry<String, String> entry : addedStageMap.entrySet()) {
            filePathId.put(entry.getKey(), entry.getValue());
        }
    }


    public void rmStagedFile(StagingArea stage) {
        Map<String, String> rmStageMap = stage.getStageMap();
        for (Map.Entry<String, String> entry : rmStageMap.entrySet()) {
            filePathId.remove(entry.getKey(), entry.getValue());
        }
    }

    public void addPrevCommitMap(Map<String, String> prevCommitMap) {
        for (Map.Entry<String, String> entry : prevCommitMap.entrySet()) {
            filePathId.put(entry.getKey(), entry.getValue());
        }
    }





}
