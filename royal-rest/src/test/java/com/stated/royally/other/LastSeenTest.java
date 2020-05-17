package com.stated.royally.other;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.stated.royally.common.clan.Clan;
import com.stated.royally.common.clan.ClanMember;
import com.stated.royally.util.SampleDataUtil;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * TODO - MOVE ACTUAL TESTS TO CLANSVC & MOVE REST TO APPROPRIATE TEST FILES
 *
 * @author Nate Vardell
 * @since 8/14/2019
 */
@Log4j2
public class LastSeenTest {

    private static ObjectMapper mapper;

    @Before
    public void init() {
        mapper = JsonMapper.builder()
                .addModule(new ParameterNamesModule())
                .addModule(new Jdk8Module())
                .addModule(new JavaTimeModule())
                .build();
    }


    @Test
    public void givenClan_mapToJavaObject_Gson() throws IOException {
        Clan clan = mapper.readValue(SampleDataUtil.getData("clan"), Clan.class);
        assertThat(clan.getClanWarTrophies(), is(1635));
        assertThat(clan.getClanScore(), is(48489));
        assertThat(clan.getMemberList().size(), is(49));
    }


    @Test
    public void givenClan_mapToJavaObject_Jackson() throws IOException {
        ClanMember member = mapper.readValue(SampleDataUtil.getData("clanMember"), ClanMember.class);
        assertThat(member.getDonations(), is(294));
        assertThat(member.getExpLevel(), is(13));
    }


    @Test
    public void zonedDateTimePatterns_new() {
        String lastSeen = "20200516T203028.000Z"; // 4:30pm My Time

        ZonedDateTime lastSeenTime = ZonedDateTime.parse(lastSeen, DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss.SSSz"));
        ZonedDateTime currentTime = ZonedDateTime.now();

        DecimalFormat df2 = new DecimalFormat("#.####");
        double dd =  ChronoUnit.SECONDS.between(lastSeenTime, currentTime) / 86400.00;
        System.out.format("%.3f", dd);

        System.out.println("\nlastSeenTime =" + lastSeenTime + "\ncurrentTime =" + currentTime);
        System.out.println("\nDuration Seconds = " + Duration.between(lastSeenTime, currentTime).getSeconds());
        System.out.println("\nChrono Unit Seconds = " + ChronoUnit.SECONDS.between(lastSeenTime, currentTime));
        System.out.println("\n\nDays Gone = " + df2.format((float)ChronoUnit.SECONDS.between(lastSeenTime, currentTime)/86400));
        System.out.format("\n\nDays Gone = %.3f", ChronoUnit.SECONDS.between(lastSeenTime, currentTime)/86400.00);
    }
}
