package com.ravi.ipldashboard.data;

import com.ravi.ipldashboard.model.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final String[] FIELD_NAMES = new String[]{
            "ID","City","Date","Season",
            "MatchNumber","Team1","Team2",
            "Venue","TossWinner","TossDecision",
            "SuperOver","WinningTeam","WonBy",
            "Margin","method","Player_of_Match",
            "Team1Players","Team2Players",
            "Umpire1","Umpire2"
    };
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource("IPL_Matches_2008_2022Data.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .targetType(MatchInput.class)
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .sql("INSERT INTO match(Id,city,date,season," +
                        "match_Number,team1,team2,venue,toss_Winner," +
                        "toss_Decision,super_Over,winning_Team,won_By," +
                        "margin,player_Of_Match,team1_Players," +
                        "team2_Players," +
                        "umpire1,umpire2) VALUES (:ID,:City,:Date,:Season,:MatchNumber,:Team1,:Team2,:Venue,:TossWinner,:TossDecision,:SuperOver,:WinningTeam,:WonBy,:Margin,:method,:Player_of_Match,:Team1Players,:Team2Players,:Umpire1,:Umpire2)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<MatchInput> reader, MatchDataProcessor processor, JdbcBatchItemWriter<Match> writer) {
        return new StepBuilder("step1", jobRepository)
                .<MatchInput, Match> chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
