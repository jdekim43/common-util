package kr.jadekim.common.util.ext

import java.time.*

val UTC = ZoneOffset.UTC
val KST = ZoneId.of("Asia/Seoul")

fun utc() = ZonedDateTime.now(UTC)

fun kst() = ZonedDateTime.now(KST)

fun utcLocalTime() = utc().toLocalDateTime()

fun kstLocalTime() = kst().toLocalDateTime()

fun ZonedDateTime.toUtc() = toZone(UTC)

fun ZonedDateTime.toKst() = toZone(KST)

fun ZonedDateTime.toZone(zone: ZoneId) = withZoneSameInstant(zone)

fun ZonedDateTime.toUtcLocal() = toUtc().toLocalDateTime()

fun ZonedDateTime.toKstLocal() = toKst().toLocalDateTime()

fun ZonedDateTime.dropTime() = withHour(0)
    .withMinute(0)
    .withSecond(0)
    .withNano(0)

fun LocalDateTime.atUtc() = atZone(UTC)

fun LocalDateTime.dropTime() = withHour(0)
    .withMinute(0)
    .withSecond(0)
    .withNano(0)

fun LocalDate.atUtc() = atStartOfDay(UTC)