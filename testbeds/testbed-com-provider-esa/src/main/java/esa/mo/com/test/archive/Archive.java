/* ----------------------------------------------------------------------------
 * Copyright (C) 2013      European Space Agency
 *                         European Space Operations Centre
 *                         Darmstadt
 *                         Germany
 * ----------------------------------------------------------------------------
 * System                : CCSDS MO COM Testbed ESA provider
 * ----------------------------------------------------------------------------
 * Licensed under the European Space Agency Public License, Version 2.0
 * You may not use this file except in compliance with the License.
 *
 * Except as expressly set forth in this License, the Software is provided to
 * You on an "as is" basis and without warranties of any kind, including without
 * limitation merchantability, fitness for a particular purpose, absence of
 * defects or errors, accuracy or non-infringement of intellectual property rights.
 * 
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * ----------------------------------------------------------------------------
 */
package esa.mo.com.test.archive;

import java.util.Iterator;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetails;
import org.ccsds.moims.mo.com.archive.structures.ArchiveDetailsList;
import org.ccsds.moims.mo.com.structures.ObjectType;
import org.ccsds.moims.mo.mal.structures.ElementList;
import org.ccsds.moims.mo.mal.structures.IdentifierList;
import org.ccsds.moims.mo.mal.structures.LongList;
import org.ccsds.moims.mo.testbed.util.LoggingBase;

/**
 *
 * Actual archive which holds all the archive objects
 */
public class Archive {

    private ArchiveObjectList archiveObjects = new ArchiveObjectList();

    private final static String KEY_SEP = "$";
    private final static String CLS = "Archive";
    /* Singleton object */
    static private Archive instance = null;

    /**
     * Returns the instance of the archive
     *
     * @return the instance
     */
    public static Archive inst() {
        if (instance == null) {
            instance = new Archive();
        }
        return instance;
    }

    /**
     * Adds an object to the archive
     *
     * @param objectType Object Type of object
     * @param domain Domain of Object
     * @param archiveDetails Archive details for object
     * @param element Element (body) part of the object can be null
     */
    public void add(ObjectType objectType, IdentifierList domain,
            ArchiveDetails archiveDetails, Object element) {
        String key = archiveObjectKey(objectType, domain, archiveDetails);
        archiveObjects.add(new ArchiveObject(objectType, domain, archiveDetails, element));

        LoggingBase.logMessage(CLS + ":store:" + key + ":" + archiveDetails);
        if (element != null) {
            LoggingBase.logMessage(CLS + ":store:ele" + element);
        }
    }

    /**
     * Returns the key for an archive object
     *
     * @param objectType
     * @param domain
     * @param archiveDetails
     * @return
     */
    private String archiveObjectKey(ObjectType objectType, IdentifierList domain,
            ArchiveDetails archiveDetails) {
        return objectType + KEY_SEP + domain + KEY_SEP + archiveDetails.getInstId();
    }

    /**
     * Checks if objectType matches a specified value - includes wildcard check
     *
     * @param objectType Object Type to be checked
     * @param rtrObjectType Object Type to be checked against can include
     * wildcards
     * @return result of check
     */
    public static boolean objectTypeMatches(ObjectType objectType, ObjectType rtrObjectType) {
        boolean bMatch;
        LoggingBase.logMessage(CLS + ":objectTypeMatches:" + objectType + ":" + rtrObjectType);
        if (!(rtrObjectType.getArea() != null && (rtrObjectType.getArea().getValue() == 0
                || (rtrObjectType.getArea().getValue() == objectType.getArea().getValue())))) {
            bMatch = false;
            LoggingBase.logMessage(CLS + ":objectTypeMatches:Area doesn't match ");
        } else if (!(rtrObjectType.getService() != null && (rtrObjectType.getService().getValue() == 0
                || rtrObjectType.getService().equals(objectType.getService())))) {
            bMatch = false;
            LoggingBase.logMessage(CLS + ":objectTypeMatches:Service doesn't match ");
        } else if (!(rtrObjectType.getVersion() != null && (rtrObjectType.getVersion().getValue() == 0
                || rtrObjectType.getVersion().equals(objectType.getVersion())))) {
            bMatch = false;
        } else if (!(rtrObjectType.getNumber() != null && (rtrObjectType.getNumber().getValue() == 0
                || rtrObjectType.getNumber().equals(objectType.getNumber())))) {
            bMatch = false;
        } else {
            bMatch = true;
        }

        LoggingBase.logMessage(CLS + ":objectTypeMatches:RET " + bMatch + ":" + objectType + ":" + rtrObjectType);

        return bMatch;
    }

    /**
     * Retrieves a number of objects from the archive
     *
     * @param rtrObjectType Object Type of objects to be retrieved can contain
     * wildcards
     * @param rtrDomain Domain of objects to be retrieved, if null no domain
     * check performed
     * @param rtrInstIds Instance Identifier of objects to be retrieved
     * @param retrievAll Boolean value if set to all instance identifier check
     * not performed
     * @return retrieved objects
     */
    public ArchiveObjectList retrieve(ObjectType rtrObjectType, IdentifierList rtrDomain,
            LongList rtrInstIds, boolean retrievAll) {
        LoggingBase.logMessage(CLS + ":retrieve:" + rtrObjectType + ":" + rtrDomain + ":" + rtrInstIds);
        ArchiveObjectList rtrObjects = new ArchiveObjectList();

        for (int i = 0; i < archiveObjects.size(); i++) {
            ArchiveObject nextObj = archiveObjects.get(i);
            if (objectTypeMatches(nextObj.objectType, rtrObjectType)
                    && (rtrDomain == null || nextObj.domain.equals(rtrDomain))
                    && (retrievAll || rtrInstIds.contains(nextObj.archiveDetails.getInstId()))) {
                rtrObjects.add(nextObj);
            }
        }
        LoggingBase.logMessage(CLS + ":retrieve:RET" + rtrObjects.size());
        return rtrObjects;
    }

    /**
     * Updates an object in the archive
     *
     * @param objectType
     * @param domain
     * @param archiveDetailsList
     * @param elementList
     */
    public void update(ObjectType objectType, IdentifierList domain,
            ArchiveDetailsList archiveDetailsList, ElementList elementList) {
        LoggingBase.logMessage(CLS + ":update:" + objectType + ":" + domain + ":"
                + archiveDetailsList + ":" + elementList);

        for (int archCnt = 0; archCnt < archiveObjects.size(); archCnt++) {
            ArchiveObject nextObj = archiveObjects.get(archCnt);
            if (nextObj.objectType.equals(objectType)
                    && nextObj.domain.equals(domain)) {
                // objectType & domain match check instId is in archiveDetailsList
                for (int detailsCnt = 0; detailsCnt < archiveDetailsList.size(); detailsCnt++) {
                    if (nextObj.getArchiveDetails().getInstId().equals(
                            archiveDetailsList.get(detailsCnt).getInstId())) {
                        LoggingBase.logMessage(CLS + ":update:element " + archCnt);
                        archiveObjects.set(archCnt, new ArchiveObject(objectType, domain,
                                archiveDetailsList.get(detailsCnt), elementList.get(detailsCnt)));
                    }
                }

            }
        }

        LoggingBase.logMessage(CLS + ":update:RET");
    }

    /**
     * Delete entries matching the specified criteria
     *
     * @param objectType type of objects to be deleted
     * @param domain domain of objects to be deleted
     * @param instIds instance identifiers of objects to be deleted
     * @param retrievAll indicates if all instances are to be deleted
     * @return list containing instance identifers of objects deleted
     */
    public LongList delete(ObjectType objectType, IdentifierList domain,
            LongList instIds, boolean retrievAll) {
        LoggingBase.logMessage(CLS + ":delete:" + objectType + ":" + domain + ":" + instIds);
        LongList deletedObjects = new LongList();

        Iterator<ArchiveObject> it = archiveObjects.iterator();
        // Iterate through all archived objects
        while (it.hasNext()) {
            ArchiveObject nextObj = it.next();
            if (nextObj.objectType.equals(objectType)
                    && nextObj.domain.equals(domain)
                    && (retrievAll || instIds.contains(nextObj.archiveDetails.getInstId()))) {
                deletedObjects.add(nextObj.archiveDetails.getInstId());
                it.remove();
                LoggingBase.logMessage(CLS + ":delete:Delete" + nextObj);
            }
        }
        LoggingBase.logMessage(CLS + ":delete:RET" + deletedObjects.size());
        return deletedObjects;
    }

    public void reset() {
        LoggingBase.logMessage(CLS + ":reset:");
        archiveObjects.clear();
    }

    public boolean contains(ObjectType objectType, IdentifierList domain, long instId) {
        for (int i = 0; i < archiveObjects.size(); i++) {
            ArchiveObject nextObj = archiveObjects.get(i);
            if ((nextObj.getArchiveDetails().getInstId().longValue() == instId)
                    && objectTypeMatches(nextObj.getObjectType(), objectType)
                    && nextObj.getDomain().equals(domain)) {
                LoggingBase.logMessage(CLS + ":contains:true:" + objectType + ":" + instId
                        + nextObj.getObjectType() + ":" + nextObj.getArchiveDetails().getInstId());
                return true;

            }

        }
        return false;
    }

    public class ArchiveObject {

        private ArchiveDetails archiveDetails;
        private Object element;
        private IdentifierList domain;
        private ObjectType objectType;

        public ArchiveObject(ObjectType objectType, IdentifierList domain,
                ArchiveDetails archiveDetails, Object element) {
            this.archiveDetails = archiveDetails;
            this.element = element;
            this.domain = domain;
            this.objectType = objectType;
        }

        public IdentifierList getDomain() {
            return domain;
        }

        public ArchiveDetails getArchiveDetails() {
            return archiveDetails;
        }

        public ObjectType getObjectType() {
            return objectType;
        }

        public Object getElement() {
            return element;
        }

        public String toString() {
            if (element != null) {
                return objectType.toString() + ":"
                        + domain.toString() + ":"
                        + archiveDetails.toString() + ":"
                        + element.toString();
            } else {
                return objectType.toString() + ":"
                        + domain.toString() + ":"
                        + archiveDetails.toString() + ":";
            }
        }

    }

    /* Simply class just makes it slightly easy to create & reference a list of
   * ArchiveObjects
     */
    public static class ArchiveObjectList extends java.util.ArrayList<ArchiveObject> {
    }

}
