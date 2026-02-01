# ProjectKorra – Remaining Issues (Post-Scan)

## Fixed This Scan

- **DBConnection.convertOldCooldownsTable()** – Both `ResultSet` usages now use try-with-resources; INSERT loop uses parameterized `modifyQuery(..., false, uuid, cooldown, cooldownTime)`.
- **OfflineBendingPlayer** – All `readQuery`/`modifyQuery` calls now use `?` placeholders and params (cooldowns, temp_elements, subelement, element, slots, permaremoved).
- **StatisticsManager / StatisticsMethods** – All stat/uuid/statId SQL uses parameterized `readQuery` and `modifyQuery`.
- **TempBlock** – `instances_` value type changed from `LinkedList<TempBlock>` to `CopyOnWriteArrayList<TempBlock>` for thread-safe list access; `getAll()` return type is `List<TempBlock>`.
- **LogFormatter** – Replaced `SimpleDateFormat` with `DateTimeFormatter` and `Instant.ofEpochMilli(record.getMillis())` for thread-safe log timestamps.

---

## Remaining (Lower Priority / Optional)

### 1. printStackTrace in logging utilities (intentional)

- **LogFormatter.java** (line 35), **LogFilter.java** (line 78) – Use `ex.printStackTrace(new PrintWriter(writer))` to format the stack trace into the log string. This is intentional (writing to a `StringWriter`, not stderr). No change required unless you want to switch to a different stack-trace formatting approach.

### 2. ToStringBuilder.reflectionToString (optional)

- **BendingPlayer**, **OfflineBendingPlayer**, **TempArmor**, **HorizontalVelocityTracker**, **FireComboStream**, **CoreAbility** – `toString()` uses `ToStringBuilder.reflectionToString()`, which can expose internal fields and is relatively heavy. Optional cleanup: override `toString()` with explicit field concatenation if you want tighter control or less reflection.

### 3. REVERT_QUEUE – PriorityQueue not thread-safe

- **TempBlock** – `REVERT_QUEUE` is a `PriorityQueue`. It is only used from the main thread (BendingManager and the runTask callback), so no change is strictly required. If you later touch it from another thread, switch to a thread-safe ordered structure (e.g. `PriorityBlockingQueue` or synchronize access).

---

## Summary

| Category              | Count | Action taken / suggestion                          |
|-----------------------|-------|----------------------------------------------------|
| ResultSet leak        | 1     | Fixed – DBConnection uses try-with-resources       |
| SQL parameterization  | Many  | Fixed – OfflineBendingPlayer, StatisticsManager/Methods, DBConnection |
| LinkedList thread     | 1     | Fixed – TempBlock uses CopyOnWriteArrayList        |
| LogFormatter date     | 1     | Fixed – java.time DateTimeFormatter                |
| printStackTrace       | 2     | Left as-is (intentional log formatting)           |
| reflectionToString    | 6     | Optional refactor                                  |
| REVERT_QUEUE thread   | 1     | Note only; main-thread-only usage                  |
