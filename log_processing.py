import sys

if __name__ == "__main__":
    TSs = []
    TJs = []
    with open(sys.argv[1],"r+") as f:
        lines = f.readlines()
        for line in lines:
            temp = line.split(',')
            if(len(temp) > 1):
                TSs.append(int(temp[0].split('=')[1]))
                TJs.append(int(temp[1].split('=')[1].strip("\n")))
    print("TS : ", (sum(TSs)/len(TSs)), "TJ : ", (sum(TJs)/len(TJs)))