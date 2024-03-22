package com.ravi.ipldashboard.data;

import com.ravi.ipldashboard.model.Team;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;


    @Autowired
    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            Map<String, Team> teamData = new HashMap<>();
            em.createQuery("SELECT m.Team1, COUNT(*) FROM Match m GROUP BY m.Team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (Long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(),team));

            em.createQuery("SELECT m.Team2, COUNT(*) FROM Match m GROUP BY m.Team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        team.setTotalMatches(team.getTotalMatches()+(long)e[1]);
                    });

            em.createQuery("SELECT m.WinningTeam, COUNT(*) FROM Match m WHERE m.WinningTeam IS NOT NULL GROUP BY m.WinningTeam", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        if(team != null){
                            team.setTotalwins((long)e[1]);
                        }
                    });
            teamData.values()
                    .forEach(team -> em.persist(team));
            teamData.values()
                    .forEach(team -> System.out.println(team));
        }
    }
}


