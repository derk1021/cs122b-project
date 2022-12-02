import sys

if __name__ == "__main__":
    TSs = []
    TJs = []
    with open(sys.argv[1]) as f:
        lines = f.readlines()
        for line in lines:
            temp = line.split(',')
            TSs.append(int(temp[0]))
            TJs.append(int(temp[1]))
    print("TS : ", (sum(TSs)/len(TSs)), "TJ : ", (sum(TJs)/len(TJs)))