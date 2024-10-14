package com.threeping.mudium.customticket.aggregate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCustomTicketId {

    private Long userId;
    private Long customTicketId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCustomTicketId that = (UserCustomTicketId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(customTicketId, that.customTicketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, customTicketId);
    }
}
