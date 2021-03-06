package smartrochambeau;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Test;

import smartrochambeau.GameModerator;
import smartrochambeau.GameSerializer;

import static org.junit.Assert.*;


public class TestGameSerializer {

  @After
  public void teardown() {
    // delete the serialized data after each test
    new File(GameSerializer.SERIALIZATION_FILE_NAME).delete();
  }

  @Test
  public void testSaveRestoreGame() throws IOException, ClassNotFoundException {
    GameSerializer.saveGame(new GameModerator());
    GameModerator actual = GameSerializer.restoreGame();
    assertNotNull(actual);
  }

  @Test(expected = IOException.class)
  public void testRestoreWithoutSaveData() throws ClassNotFoundException, IOException {
    GameSerializer.restoreGame();
  }
}
