#!/bin/bash

#declare -a rats=(50 100 150 200 250 300 350 400)
#declare -a pipers=(4 5 6 7 8 9 10)

declare -a rats=(100)
declare -a pipers=(6)


for piper in ${pipers[@]}; do
    for rat in ${rats[@]}; do
        for i in $(seq 1 10); do 
            echo -e "\n";
            echo $i, $rat, $piper;
            java pppp.sim.Simulator --side 100 --groups g1 g2 g8 g3 -p $piper -r $rat --turns 300;
        done
    done
done
