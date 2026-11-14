# ScoreKeeper practice project

In VS Code open this folder, select src/GameNight.java, then click Run above main.
Enter H 2, A 1, and Q on separate lines. The final score should be Home 2 - Away 1.
Run src/SeasonReport.java for the four-game season summary. For tests, use the
Testing panel or the JUnit console launcher.

## Component design

ScoreKeeperKernel extends OSU Standard and defines score and award.
ScoreKeeper extends ScoreKeeperKernel with isTied and margin.
ScoreKeeperSecondary implements ScoreKeeper using only public operations.
ScoreKeeper1 extends ScoreKeeperSecondary and owns the two stored scores.
The array keeps lookup and scoring constant-time. No array escapes to callers.

## Verification plan

| Behavior | Inputs and expected observations |
| --- | --- |
| Construction and scoring | Start at 0-0. Award zero, then award twice to either team. Only the selected side gains points. |
| Numeric boundaries | Reach the maximum int. Another point and a negative award must fail and preserve the score. |
| Comparisons | Check leads from each perspective, ties at zero and at the maximum, and the maximum positive and negative margins. |
| Standard operations | Create from a populated board, clear twice, transfer onto a populated board, then mutate the source. Check independence, replacement, source reset, empty transfer, and rejected self or null transfer. |
| Common methods | Check reflexivity, equal values and hashes, unequal home or away scores, null and unrelated types, and the displayed score. |
| Live game | Try a malformed command, negative points, both teams scoring, then Q. Valid entries must still work after an error. |
| Season analysis | Aggregate two home wins, a loss, and a draw. Expect the W-L-T record 2-1-1. |

## Reproducing the checks

Use JDK 21, the OSU components library and JUnit Platform Console Standalone
1.11.4. This local copy includes both jars under lib. With JDK 21 on PATH,
run these commands in PowerShell from this folder.

```powershell
javac -cp 'lib/*' -d bin src/*.java test/*.java
java -jar lib/junit.jar execute -cp 'bin;lib/components.jar' --scan-class-path
java -cp 'bin;lib/*' GameNight
java -cp 'bin;lib/*' SeasonReport
```
