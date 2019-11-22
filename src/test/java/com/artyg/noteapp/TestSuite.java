package com.artyg.noteapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({NoteControllerIntegrationTest.class, NoteRepositoryIntegrationTest.class})
public class TestSuite {
}
