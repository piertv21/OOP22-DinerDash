package it.unibo.dinerdash.model.api.States;
/** States that can be used for Customer.
 *  {@link #LINE}
 *  {@link #ANGRY}
 *  {@link #WALKING}
 *  {@link #THINKING}
 *  {@link #ORDERING}
 */
public enum CustomerState {
    /** customer waiting in line.
     */
    LINE,
    /** customer whom waited too much ,and want to leave.
     */
    ANGRY,
    /** customer walking to the table.
     */
    WALKING,
    /** customer thinking about what to order.
     */
    THINKING,
    /** customer wanting to order.
     */
    ORDERING
}
