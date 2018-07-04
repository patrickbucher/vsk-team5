package ch.hslu.vsk18fs.g05.loggercommon.message;

import java.io.Serializable;
import java.time.Instant;

public class Message implements LogMessage, Serializable {

  private static final long serialVersionUID = 1L;
  private final String level;
  private final Instant creationTimestamp;
  private final Instant serverEntryTimestamp;
  private final String message;
  private final String source;

  public Message(final String level, final String message) {
    this.level = level;
    this.message = message;
    this.creationTimestamp = Instant.now();
    this.serverEntryTimestamp = Instant.now();
    this.source = "";
  }

  public Message(final String level, final Instant creationTimestamp,
      final Instant serverEntryTimestamp, final String message, final String source) {
    super();
    this.level = level;
    this.creationTimestamp = creationTimestamp;
    this.serverEntryTimestamp = serverEntryTimestamp;
    this.message = message;
    this.source = source;
  }

  @Override
  public String getLevel() {
    return this.level;
  }

  @Override
  public Instant getCreationTimestamp() {
    return this.creationTimestamp;
  }

  @Override
  public Instant getServerEntryTimestamp() {
    return this.serverEntryTimestamp;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  @Override
  public String getSource() {
    return this.source;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + (this.creationTimestamp == null ? 0 : this.creationTimestamp.hashCode());
    result = prime * result + (this.level == null ? 0 : this.level.hashCode());
    result = prime * result + (this.message == null ? 0 : this.message.hashCode());
    result = prime * result
        + (this.serverEntryTimestamp == null ? 0 : this.serverEntryTimestamp.hashCode());
    result = prime * result + (this.source == null ? 0 : this.source.hashCode());
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final Message other = (Message) obj;
    if (this.creationTimestamp == null) {
      if (other.creationTimestamp != null) {
        return false;
      }
    } else if (!this.creationTimestamp.equals(other.creationTimestamp)) {
      return false;
    }
    if (this.level == null) {
      if (other.level != null) {
        return false;
      }
    } else if (!this.level.equals(other.level)) {
      return false;
    }
    if (this.message == null) {
      if (other.message != null) {
        return false;
      }
    } else if (!this.message.equals(other.message)) {
      return false;
    }
    if (this.serverEntryTimestamp == null) {
      if (other.serverEntryTimestamp != null) {
        return false;
      }
    } else if (!this.serverEntryTimestamp.equals(other.serverEntryTimestamp)) {
      return false;
    }
    if (this.source == null) {
      if (other.source != null) {
        return false;
      }
    } else if (!this.source.equals(other.source)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "Message [level=" + this.level + ", creationTimestamp=" + this.creationTimestamp
        + ", serverEntryTimestamp=" + this.serverEntryTimestamp + ", message=" + this.message
        + ", source=" + this.source + "]";
  }
}
