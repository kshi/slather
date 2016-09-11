#! /bin/sh

pipers="13 13 13 13 13 13 13 13 13 13"
rats="100"
sides="100"

java=java
game=pppp/game.csv
player=pppp/player.csv

id=15
if [ $# -gt 0 ]; then
	id=$1
fi

lid=0
if [ -f $game ]; then
	lid=`tail -n 1 $game | cut -f1 -d','`
	if [ $lid == "id" ]; then
		lid=0
	fi
else
	echo "Missing CSV files"
	exit 1
fi

for p in $pipers; do
	for r in $rats; do
		for s in $sides; do
			while read g; do
				id=`expr $id + 1`
				echo java -p $p -r $r -s $s -g $g --id $id $game $player
				if [ $id -gt $lid ]; then
					$java pppp.sim.Simulator -p $p -r $r -s $s -g $g --id $id $game $player
				fi
			done < pppp/group_combinations.txt
		done
	done
done
