package com.fatecmeets.backend.token;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "auth_tokens", indexes = {
  @Index(name = "idx_token_usuario_type", columnList = "usuario_id,token_type"),
  @Index(name = "uk_token_value", columnList = "token", unique = true)
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Token {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 160)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "token_type", nullable = false, length = 20)
  private TokenType type;

  @Column(nullable = false)
  private Instant expiresAt;

  @Column(nullable = false)
  private boolean revoked = false;

  @Column(name = "usuario_id", nullable = false)
  private Long usuarioId;

  @Builder.Default
  @Column(nullable = false)
  private Instant createdAt = Instant.now();

  @PrePersist
  void prePersist() {
    if (createdAt == null) createdAt = Instant.now();
  }
}
