package org.ccsds.moims.mo.mc.action.provider;

/**
 * Interface that providers of the Action service must implement to handle
 * the operations of that service.
 */
public interface ActionHandler {

    /**
     * Implements the operation execute.
     * 
     * @param executionRequest The executionRequest field.
     * @param interaction The MAL object representing the interaction in the provider.
     * @throws org.ccsds.moims.mo.mc.DuplicateException The entry or operation is a duplicate of an existing record, violating uniqueness.
     * @throws org.ccsds.moims.mo.mc.InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws org.ccsds.moims.mo.mc.RejectedException The operation has been rejected due to policy or validation rules.
     * @throws org.ccsds.moims.mo.mal.UnknownException Operation specific.
     * @throws org.ccsds.moims.mo.mal.MALInteractionException if there is a problem during the interaction as defined by the MAL specification.
     * @throws org.ccsds.moims.mo.mal.MALException if there is an implementation exception
     */
    void execute(org.ccsds.moims.mo.mc.structures.ActionExecutionRequest executionRequest,
            org.ccsds.moims.mo.mal.provider.MALInteraction interaction) throws org.ccsds.moims.mo.mc.DuplicateException, org.ccsds.moims.mo.mc.InvalidException, org.ccsds.moims.mo.mc.RejectedException, org.ccsds.moims.mo.mal.UnknownException, org.ccsds.moims.mo.mal.MALInteractionException, org.ccsds.moims.mo.mal.MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(org.ccsds.moims.mo.mc.action.provider.ActionSkeleton skeleton);
}
