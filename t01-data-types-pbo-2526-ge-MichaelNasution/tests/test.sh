#!/bin/bash
set -e

SCORE=0
FEEDBACK=""

# ---------- Compile ----------
if javac src/Main.java 2> compile_error.txt; then
  SCORE=$((SCORE + 5))
else
  echo "❌ Compile error"
  cat compile_error.txt
  exit 1
fi

# ---------- Test Soal 1 ----------
OUT=$(echo "2147483647 1" | java -cp src Main)
if [[ "$OUT" == *"OVERFLOW"* ]]; then
  SCORE=$((SCORE + 15))
else
  FEEDBACK+="[Soal 1] Integer overflow tidak terdeteksi.\n"
fi

# ---------- Test Soal 2 ----------
OUT=$(echo "0.1 0.2" | java -cp src Main)
if [[ "$OUT" == *"0.000000"* ]]; then
  SCORE=$((SCORE + 20))
else
  FEEDBACK+="[Soal 2] Precision float vs double salah.\n"
fi

# ---------- Test Soal 3 ----------
OUT=$(echo "128" | java -cp src Main)
if [[ "$OUT" == *"==: false"* && "$OUT" == *"equals: false"* ]]; then
  SCORE=$((SCORE + 20))
else
  FEEDBACK+="[Soal 3] Salah paham == vs equals.\n"
fi

# ---------- Test Soal 4 ----------
OUT=$(echo "hello" | java -cp src Main)
if [[ "$OUT" == *"==: false"* ]]; then
  SCORE=$((SCORE + 20))
else
  FEEDBACK+="[Soal 4] String immutability tidak dipahami.\n"
fi

# ---------- Test Soal 5 ----------
OUT=$(echo "10 2.5 false" | java -cp src Main)
if [[ "$OUT" == *"-25.00"* ]]; then
  SCORE=$((SCORE + 20))
else
  FEEDBACK+="[Soal 5] Parsing atau logika boolean salah.\n"
fi

# ---------- Final ----------
echo "SCORE: $SCORE / 100"
echo -e "FEEDBACK:\n$FEEDBACK"
