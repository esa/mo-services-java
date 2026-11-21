package org.ccsds.moims.mo.mc.action.provider;

import org.ccsds.moims.mo.mal.MALException;
import org.ccsds.moims.mo.mal.UnknownException;
import org.ccsds.moims.mo.mal.provider.MALInteraction;
import org.ccsds.moims.mo.mc.DuplicateException;
import org.ccsds.moims.mo.mc.InvalidException;
import org.ccsds.moims.mo.mc.RejectedException;
import org.ccsds.moims.mo.mc.structures.ActionExecutionRequest;

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
     * @throws DuplicateException The entry or operation is a duplicate of an existing record, violating uniqueness.
     * @throws InvalidException The input data or operation format is invalid and does not meet required criteria.
     * @throws RejectedException The operation has been rejected due to policy or validation rules.
     * @throws UnknownException Operation specific.
     * @throws MALException if there is an implementation exception
     */
    void execute(ActionExecutionRequest executionRequest,
            MALInteraction interaction) throws DuplicateException, InvalidException, RejectedException, UnknownException, MALException;
    /**
     * Sets the skeleton to be used for creation of publishers.
     * 
     * @param skeleton The skeleton to be used.
     */
    void setSkeleton(ActionSkeleton skeleton);
}
