package com.straujupite.common.util.addcomment;

import com.straujupite.common.dto.common.AddCommentTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CommentBuilderTest {
    public static final String STR_NUMBER = "str-number";
    public static final String COMPANY_NUMBER = "company-number";
    private static final String DATE = "10-07-2001";
    private static final String TIME = "10:10:10";
    private static final String CONTACT_NAME = "contact-name";

    @Mock
    Clock clock;

    @InjectMocks
    private CommentBuilder victim;

    @BeforeEach
    void setUp() {
        LocalDateTime mockTime = LocalDateTime.of(2001, 7, 10, 10, 10, 10);
        Mockito.when(clock.getZone()).thenReturn(ZoneId.systemDefault());
        Mockito.when(clock.instant()).thenReturn(mockTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    void shouldReturnCallSuccessfullComment() {
        var result = victim.buildCommentByTemplate(AddCommentTemplate.CALL_SUCCESSFUL_TEMPLATE, COMPANY_NUMBER, STR_NUMBER);

        assertNotNull(result);
        assertEquals("✅ Zvans ar klientu (tālr. nr. "
                        + COMPANY_NUMBER
                        + ") no tālr. nr. "
                        + STR_NUMBER
                        + " pabeigts. DATUMS UN LAIKS: "
                        + DATE + " plkst. "
                        + TIME,
                result.getValue()
        );
    }

    @Test
    void shouldReturnCallUnsuccessfullComment() {
        var result = victim.buildCommentByTemplate(AddCommentTemplate.CALL_UNSUCCESSFUL_TEMPLATE, COMPANY_NUMBER, STR_NUMBER);

        assertNotNull(result);
        assertEquals("❌ Neizdevās sazvanīt klientu (tālr. nr. company-number) no tālr. nr. "
                        + STR_NUMBER
                        + " DATUMS UN LAIKS: "
                        + DATE
                        + " plkst. "
                        + TIME,
                result.getValue()
        );
    }

    @Test
    void shouldReturnLostCallerAddedComment() {
        var result = victim.buildCommentByTemplate(AddCommentTemplate.LOST_CALLER_ADDED, STR_NUMBER, CONTACT_NAME);

        assertNotNull(result);
        assertEquals("IENĀKOŠS ZVANS: ❌ Klientam neizdevās sazvanīt tālr. nr. " + STR_NUMBER
                        + " (" + CONTACT_NAME + ")."
                        + " DATUMS UN LAIKS: " + DATE + " plkst. " + TIME,
                result.getValue()
        );
    }

}
